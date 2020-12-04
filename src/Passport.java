public class Passport {
    private static final String BIRTH_YEAR = "byr";
    private static final String ISSUE_YEAR = "iyr";
    private static final String EXPIRATION_YEAR = "eyr";
    private static final String HEIGHT = "hgt";
    private static final String HAIR_COLOR = "hcl";
    private static final String EYE_COLOR = "ecl";
    private static final String PASSPORT_ID = "pid";
    private static final String COUNTRY_ID = "cid";
    private static final String HEX_PATTERN = "^#([a-f0-9]{6})$";
    private static final String PID_PATTERN = "^([0-9]{9})$";

    private String birthYear;
    private String issueYear;
    private String expYear;
    private String height;
    private String hairColor;
    private String eyeColor;
    private String passportId;
    private String countryId;

    public Passport(String birthYear, String issueYear, String expYear, String height, String hairColor, String eyeColor, String passportId, String countryId) {
        this.birthYear = birthYear;
        this.issueYear = issueYear;
        this.expYear = expYear;
        this.height = height;
        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
        this.passportId = passportId;
        this.countryId = countryId;
    }

    public Passport(String inputLine) {
        String[] fields = inputLine.split(" ");
        for (String field : fields) {
            String[] data = field.split(":");
            String identifier = data[0];
            switch (identifier) {
                case BIRTH_YEAR -> setBirthYear(data[1]);
                case ISSUE_YEAR -> setIssueYear(data[1]);
                case EXPIRATION_YEAR -> setExpYear(data[1]);
                case HEIGHT -> setHeight(data[1]);
                case HAIR_COLOR -> setHairColor(data[1]);
                case EYE_COLOR -> setEyeColor(data[1]);
                case PASSPORT_ID -> setPassportId(data[1]);
                case COUNTRY_ID -> setCountryId(data[1]);
            }
        }

    }

    public boolean isValid() {
        return ((getBirthYear() != null) &&
                (getIssueYear() != null) &&
                (getExpYear() != null) &&
                (getHeight() != null) &&
                (getHairColor() != null) &&
                (getEyeColor() != null) &&
                (getPassportId() != null));
    }

    public boolean isValidFull() {
        return (validBirthYear() && validIssueYear() && validExpYear() &&
                validHeight() && validHairColor() && validEyeColor() && validPassportId());
    }

    @Override
    public String toString() {
        String valid = isValid() ? "[VALID]" : "[INVALID]";
        return valid + ", BirthYear: " + getBirthYear() + ", IssueYear: " + getIssueYear() +
                ", ExpYear: " + getExpYear() + ", Height: " + getHeight() +
                ", HairColor: " + getHairColor() + ", EyeColor: " + getEyeColor() +
                ", PassportId: " + getPassportId() + ", CountryId: " + getCountryId();
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public boolean validBirthYear() {
        if (getBirthYear() == null) return false;
        try {
            int byear = Integer.parseInt(getBirthYear());
            return ((byear >= 1920) && (byear <= 2002));
        } catch (Exception e) {
            return false;
        }
    }

    public String getIssueYear() {
        return issueYear;
    }

    public void setIssueYear(String issueYear) {
        this.issueYear = issueYear;
    }

    public boolean validIssueYear() {
        if (getIssueYear() == null) return false;
        try {
            int iyear = Integer.parseInt(getIssueYear());
            return ((iyear >= 2010) && (iyear <= 2020));
        } catch (Exception e) {
            return false;
        }
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public boolean validExpYear() {
        if (getExpYear() == null) return false;
        try {
            int eyear = Integer.parseInt(getExpYear());
            return ((eyear >= 2020) && (eyear <= 2030));
        } catch (Exception e) {
            return false;
        }
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public boolean validHeight() {
        if ((getHeight() == null) || (getHeight().length() < 4)) return false;
        String hend = getHeight().substring(getHeight().length()-2);
        try {
            int size = Integer.parseInt(getHeight().substring(0, getHeight().length()-2));
            if (hend.equalsIgnoreCase("cm")) {
                return ((size >= 150) && (size <= 193));
            } else if (hend.equalsIgnoreCase("in")) {
                return ((size >= 59) && (size <= 76));
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public boolean validHairColor() {
        if ((getHairColor() == null) || (getHairColor().length() != 7)) return false;
        return getHairColor().matches(HEX_PATTERN);
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public boolean validEyeColor() {
        if (getEyeColor() == null) return false;
        return (getEyeColor().equalsIgnoreCase("amb") ||
                getEyeColor().equalsIgnoreCase("blu") ||
                getEyeColor().equalsIgnoreCase("brn") ||
                getEyeColor().equalsIgnoreCase("gry") ||
                getEyeColor().equalsIgnoreCase("grn") ||
                getEyeColor().equalsIgnoreCase("hzl") ||
                getEyeColor().equalsIgnoreCase("oth"));
    }

    public String getPassportId() {
        return this.passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public boolean validPassportId() {
        if ((getPassportId() == null) || (getPassportId().length() != 9)) return false;
        return getPassportId().matches(PID_PATTERN);
    }

    public String getCountryId() {
        return this.countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}
