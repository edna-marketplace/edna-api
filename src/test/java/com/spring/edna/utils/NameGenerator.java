package com.spring.edna.utils;

import java.util.Random;

public class NameGenerator {
    private static final String[] FIRST_NAMES = {
            "Júlia", "Victor", "Carla", "Arthur", "Eduarda", "Fernanda", "Vilmar", "Emanuel", "Adriano", "André",
            "Larissa", "Giuseppe", "Natália", "Luciano", "Patrícia", "Nathan", "Simone", "Tiago", "Vanessa", "William",
            "Beatriz", "Caleb", "Daniele", "Jonatan", "Felipe", "Pedro", "Helena", "Igor", "Juliana", "Leonardo",
            "Mirela", "Vitor", "Victória", "Pietro", "Lucio", "Maria", "Tatiana", "Vinícius", "Yasmin", "Matheus",
    };

    private static final String[] LAST_NAMES = {
            "Sakuma", "Leitão", "Caldara", "Castro", "Barcelos", "Ferreira", "Stosick", "Lapa", "Martins", "Morsch",
            "Gomes", "Ribeiro", "Carvalho", "Alves", "Pereira", "Silva", "Barbosa", "Correia", "Mendes", "Nunes",
            "Cavalcanti", "Cardoso", "Pinto", "Teixeira", "Moraes", "Andrade", "Vieira", "Campos", "Araújo", "Monteiro",
            "Freitas", "Dias", "Junior", "Assis", "Figueiredo", "Tavares", "Guimarães", "Melo", "Xavier", "Rezende"
    };

    private static final Random random = new Random();

    public static String generateName() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }

    public static void main(String[] args) {
        System.out.println(generateName());
    }
}
