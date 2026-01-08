package lsdi.IndoorBackend.common;

import lsdi.IndoorBackend.dtos.CreateOrganizationDTO;

public class OrganizationConstants {
    public static String ROOT_ORG_NAME = "UFMA";

    public static CreateOrganizationDTO ROOT_ORG = new CreateOrganizationDTO(
            ROOT_ORG_NAME,
            null,
            null
    );

    public static String CHILD_ORG_NAME = "LSDI";
    public static String CEP = "65080-805";
}
