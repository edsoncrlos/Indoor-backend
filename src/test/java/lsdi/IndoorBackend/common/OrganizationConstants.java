package lsdi.IndoorBackend.common;

import lsdi.IndoorBackend.dtos.OrganizationDTO;

public class OrganizationConstants {
    public static String ROOT_ORG_NAME = "UFMA";

    public static OrganizationDTO ROOT_ORG = new OrganizationDTO(
            ROOT_ORG_NAME,
            null,
            null
    );

    public static String CHILD_ORG_NAME = "LSDI";
    public static String CEP = "65080-805";
}
