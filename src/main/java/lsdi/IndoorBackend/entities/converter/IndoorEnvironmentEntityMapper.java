package lsdi.IndoorBackend.entities.converter;

import lsdi.IndoorBackend.domain.model.IndoorEnvironment;
import lsdi.IndoorBackend.entities.IndoorEnvironmentEntity;
import lsdi.IndoorBackend.entities.OrganizationEntity;

public final class IndoorEnvironmentEntityMapper {

    private IndoorEnvironmentEntityMapper() {}

    public static IndoorEnvironmentEntity toEntity(
            IndoorEnvironment domain,
            OrganizationEntity organization
    ) {
        IndoorEnvironmentEntity rootIndoorEnvironment = new IndoorEnvironmentEntity(
                domain.getName(),
                domain.getBeaconsSignalStatistics(),
                organization
        );

        mapChildren(domain, rootIndoorEnvironment);

        return rootIndoorEnvironment;
    }

    private static void mapChildren(
            IndoorEnvironment domainParent,
            IndoorEnvironmentEntity entityParent
    ) {
        for (IndoorEnvironment childDomain : domainParent.getChildren()) {

            IndoorEnvironmentEntity childEntity =
                    new IndoorEnvironmentEntity(
                            childDomain.getName(),
                            childDomain.getBeaconsSignalStatistics(),
                            entityParent
                    );

            mapChildren(childDomain, childEntity);
        }
    }
}
