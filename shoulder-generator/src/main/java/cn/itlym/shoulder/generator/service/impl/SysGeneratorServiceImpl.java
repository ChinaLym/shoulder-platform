package cn.itlym.shoulder.generator.service.impl;

import cn.itlym.shoulder.generator.mapper.SysGeneratorMapper;
import cn.itlym.shoulder.generator.service.SysGeneratorService;
import cn.itlym.shoulder.generator.utils.GenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.shoulder.core.dto.response.PageResult;
import org.shoulder.core.log.AppLoggers;
import org.shoulder.core.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @author lym
 */
@Service
public class SysGeneratorServiceImpl implements SysGeneratorService {

    private final Logger log = AppLoggers.APP_SERVICE;

    @Autowired
    private SysGeneratorMapper sysGeneratorMapper;

    @Override
    public PageResult queryList(Map<String, Object> map) {
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(MapUtils.getInteger(map, "page"), MapUtils.getInteger(map, "limit"), true);
        List<Map<String, Object>> list = sysGeneratorMapper.queryList(map);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);

        return PageResult.PageInfoConverter.toResult(pageInfo);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return 0;
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        return sysGeneratorMapper.queryTable(tableName);
    }

    @Override
    public List<Map<String, String>> queryColumns(String tableName) {
        return sysGeneratorMapper.queryColumns(tableName);
    }

    @Override
    public byte[] generatorCode(String[] tableNames, OutputStream out) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            if (MapUtils.isEmpty(table) || CollectionUtils.isEmpty(columns)) {
                log.warn("table {} not exist or without any columns", table);
                continue;
            }
            //生成代码
            GenUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    @Override
    public byte[] generatorCode(OutputStream out) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        // 查询所有表信息
        List<Map<String, String>> tables = sysGeneratorMapper.listTable();
        for (Map<String, String> table : tables) {
            String tableName = table.get("TABLE_NAME");
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            if (MapUtils.isEmpty(table) || CollectionUtils.isEmpty(columns)) {
                log.warn("table {} not exist or without any columns", table);
                continue;
            }
            //生成代码
            GenUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }


}
