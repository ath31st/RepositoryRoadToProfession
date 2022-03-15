package phonebook.utils;

public class LinearSearching {
    public static boolean search(String fromRawData, String fromFindersData) {
        String contact = fromRawData.substring(fromRawData.indexOf(' ') + 1);
        return fromFindersData.equals(contact);
    }
}
