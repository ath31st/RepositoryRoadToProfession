package antifraud.service;

import antifraud.dto.StatusResp;
import antifraud.entity.SuspiciousIp;
import antifraud.repository.SuspiciousIpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SuspiciousIpService {
    @Autowired
    private SuspiciousIpRepository suspiciousIpRepository;
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public SuspiciousIp saveSuspiciousIpInDb(SuspiciousIp ip) {
        checkValidIpFormat(ip.getIp());
        checkExistingIpInDb(ip.getIp());
        suspiciousIpRepository.save(ip);
        return ip;
    }
    public StatusResp deleteSuspiciousIpFromDb(String ip){
        checkValidIpFormat(ip);
        SuspiciousIp tmpIp = retrieveIpFromDb(ip);
        suspiciousIpRepository.delete(tmpIp);
        return new StatusResp(String.format("IP %s successfully removed!",tmpIp.getIp()));
    }

    public List<SuspiciousIp> getIpListFromDb() {
        List<SuspiciousIp> suspiciousIps = new ArrayList<>();
        suspiciousIpRepository.findAll().iterator().forEachRemaining(suspiciousIps::add);
        return suspiciousIps;
    }

    private void checkValidIpFormat(String ip) {
        if (!PATTERN.matcher(ip).matches())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IP address has the wrong format!");
    }

    private void checkExistingIpInDb(String ip) {
        if (suspiciousIpRepository.findSuspiciousIpByIp(ip).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "IP is already in the database!");
    }
    private SuspiciousIp retrieveIpFromDb(String ip) {
        return suspiciousIpRepository.findSuspiciousIpByIp(ip)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "IP not found!"));
    }
}
