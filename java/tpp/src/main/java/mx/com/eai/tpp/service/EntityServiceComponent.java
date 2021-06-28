package mx.com.eai.tpp.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class EntityServiceComponent<E, I extends Serializable>
{

    private Logger log = Logger.getLogger(this.getClass());

    private List<Map<String, E>> entityMapList;

    @PostConstruct
    public void init()
    {
        this.entityMapList = new ArrayList<Map<String, E>>();
    }

    private String getKeyForEntity(E entity) throws Exception
    {
        if (entity != null)
        {
            try
            {
                StringBuilder key = new StringBuilder();
                key.append(String.valueOf(Math.random()).substring(2)).append("-");
                key.append(String.valueOf(System.currentTimeMillis()).substring(0, 7));
                return key.toString();
            }
            catch (Exception ex)
            {
                log.error("Error en AdmisionServiceComponent --> getKeyForEntity ", ex);
            }
        }
        return "La entidad es null";

    }

    public String addEntity(E entity) throws Exception
    {
        String key = getKeyForEntity(entity);
        try
        {
            Map<String, E> map = new HashMap<String, E>();
            map.put(key, entity);
            this.entityMapList.add(map);
            return key;
        }
        catch (Exception ex)
        {
            log.error("Error en AdmisionServiceComponent --> addEntity ", ex);
        }
        return "";
    }

    public E getEntity(String key) throws Exception
    {
        Map removeMap = null;
        E returnEntity = null;
        if (!this.entityMapList.isEmpty())
        {
            for (Map<String, E> map : entityMapList)
            {
                for (String k : map.keySet())
                {
                    if (key.equals(k))
                    {
                        returnEntity = map.get(k);
                        removeMap = map;
                        break;
                    }
                }
            }
            if (removeMap != null)
            {
                this.entityMapList.remove(this.entityMapList.indexOf(removeMap));
            }
        }
        return returnEntity;
    }
}
