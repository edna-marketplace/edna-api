package com.spring.edna.utils;

public class DocumentUtils {

    public static String stardandizeCpf(String cpf) {
        cpf.replaceAll("[.]", "");
        cpf.replaceAll("-", "");

        return cpf;
    }

    public static String stardandizeCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[.]", "");
        cnpj = cnpj.replaceAll("-", "");
        cnpj = cnpj.replaceAll("/", "");

        return cnpj;
    }
}
