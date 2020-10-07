package cn.itlym.shoulder.generator.service;

import org.shoulder.core.dto.response.PageResult;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author lym
 */
@Service
public interface SysGeneratorService {

    PageResult queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);

    byte[] generatorCode(String[] tableNames, OutputStream out);

}
