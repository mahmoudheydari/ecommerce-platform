package com.naderaria.common_core.util;

import java.net.URI;
import java.net.URISyntaxException;

public interface WebUtil {

    static URI createURI(String path, Long id) {
        try {
            return new URI(path.concat(id.toString()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
