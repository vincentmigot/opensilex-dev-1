//******************************************************************************
//                      APIExtension.java
// OpenSILEX - Licence AGPL V3.0 - https://www.gnu.org/licenses/agpl-3.0.en.html
// Copyright © INRA 2019
// Contact: vincent.migot@inra.fr, anne.tireau@inra.fr, pascal.neveu@inra.fr
//******************************************************************************
package org.opensilex.rest.extensions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.Path;
import org.opensilex.rest.RestApplication;
import org.opensilex.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extension interface for OpenSilex modules which implements REST extension
 *
 * @author Vincent Migot
 */
public interface APIExtension {

    public static final Logger LOGGER = LoggerFactory.getLogger(APIExtension.class);
    
    /**
     * This method is called during application initialization to get all
     * packages to scan for components like request filters or response mapper
     *
     * @return List of packages to scan
     */
    public default List<String> getPackagesToScan() {
        List<String> list = new ArrayList<>();
        list.addAll(apiPackages());

        return list;
    }

    /**
     * This method is called during application initialization to get all
     * packages to scan for jersey web services wich will be displayed into
     * swagger UI
     *
     * @return List of packages to scan for web services
     */
    public default Set<String> apiPackages() {
        Set<String> packageSet = new HashSet<>();
        Set<Class<?>> classes = ClassUtils.getAnnotatedClasses(Path.class);
        classes.forEach((Class<?> clazz) -> {
            packageSet.add(clazz.getPackage().getName());
        });

        return packageSet;
    }

    /**
     * This entry point allow module to initialize anything in application after
     * all configuration is loaded at the end of application loading
     *
     * @param resourceConfig API main entry point instance extending Jersey
     * {@code org.glassfish.jersey.server.ResourceConfig}
     */
    public default void initAPI(RestApplication resourceConfig) {
        LOGGER.debug("Init API for module: " + this.getClass().getCanonicalName());
    }
}
