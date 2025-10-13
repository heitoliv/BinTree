package app;

import java.util.Comparator;

public class ContactPhoneComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact c1, Contact c2) {
        return c1.getPhone().compareToIgnoreCase(c2.getPhone());
    }
}
