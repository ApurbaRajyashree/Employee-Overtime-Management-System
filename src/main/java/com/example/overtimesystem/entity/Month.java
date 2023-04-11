package com.example.overtimesystem.entity;

public enum Month {
    January(1), February(2), March(3), April(4), May(5), June(6),
    July(7), August(8), September(9), October(10),
    November(11), December(12);
    private final int month;

    Month(int month) {
        this.month = month;
    }

//    public static List<EnumDto> getGenderList() {
//        return Arrays.stream(Gender.values())
//                .map(gender -> new EnumDto(gender.key, gender.valueNepali, gender.valueEnglish))
//                .collect(Collectors.toList());
//    }
//
//    public String getLabel() {
//        return LocaleContextHolder.getLocale().getLanguage().equalsIgnoreCase("np") ? valueNepali : valueEnglish;
//    }
//}

}
