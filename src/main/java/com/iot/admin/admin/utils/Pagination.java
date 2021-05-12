package com.iot.admin.admin.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Define constantes y métodos de utilidad relacionada al manejo de datos al que
 * se requiere aplicar paginación.
 */
public class Pagination {

    /**
     * Nombre del parámetro GET que indica el número de página para una paginación
     * de resultados.
     */
    public static final String PAGE_PARAM_NAME = "page";

    /**
     * Nombre del parámetro GET que indica el número de elementos mostrados por
     * página para una paginación de resultados.
     */
    public static final String SIZE_PARAM_NAME = "size";

    /**
     * Indica el número de página por defecto.
     */
    public static final int PAGE_DEFAULT = 0;

    /**
     * Indica el número de resultados por página por defecto.
     */
    public static final int SIZE_DEFAULT = 15;

    /**
     * Retorna un diccionario con los datos de los resultados finales de una
     * paginación especificado.
     * 
     * @param page datos de la paginación.
     * @return {@link Map}
     */
    public static Map<String, Object> mapPage(Page<?> page) {
        int currentPage = page.getNumber();
        int size = page.getSize();
        int elements = page.getNumberOfElements();
        int from = elements > 0 ? (currentPage * size) + 1 : 0;
        int to = elements > 0 ? (from + elements) - 1 : 0;
        
        Map<String, Object> map = new HashMap<>();
        map.put("data", page.getContent());
        map.put("page", currentPage);
        map.put("size", size);
        map.put("elements", elements);
        map.put("total", page.getTotalElements());
        map.put("pages", page.getTotalPages());
        map.put("from", from);
        map.put("to", to);
        return map;
    }

    /**
     * Inicializa una instancia para la paginación de resultados en base a los
     * parámetros GET recibidos.
     * 
     * @param params un diccionario con los parámetros disponibles.
     * @return {@link Pageable}
     */
    public static Pageable pageRequest(Map<String, String> params) {
        int page = getIntValue(params, PAGE_PARAM_NAME, PAGE_DEFAULT);
        int size = getIntValue(params, SIZE_PARAM_NAME, SIZE_DEFAULT);
        return PageRequest.of(page, size);
    }

    /**
     * Trae un valor númerico entero para los datos de número de página o elementos
     * por página. Realiza validaciones para traer un valor correcto del diccionario
     * especificado.
     * 
     * @param params       el diccionario que contiene los parámetros de paginación.
     * @param key          el nombre del parámetro a buscar en el diccionario.
     * @param defaultValue el valor por defecto a usar en caso de que el parámetro
     *                     buscado no está en el diccionario o que su valor sea
     *                     incorrecto.
     * @return un número entero.
     */
    private static int getIntValue(Map<String, String> params, String key, int defaultValue) {
        String value = params.get(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        return defaultValue;
    }
}
