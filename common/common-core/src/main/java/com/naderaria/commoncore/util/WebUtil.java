package com.naderaria.commoncore.util;

import com.naderaria.commoncore.exception.BusinessException;
import com.naderaria.commoncore.exception.ErrorCode;

import java.net.URI;
import java.net.URISyntaxException;

public interface WebUtil {

    static URI createURI(String path, Long id) {
        try {
            return new URI(path.concat(id.toString()));
        } catch (URISyntaxException e) {
            throw new BusinessException(ErrorCode.CreateUrlException);
        }
    }

}
