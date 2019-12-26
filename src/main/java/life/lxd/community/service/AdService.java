package life.lxd.community.service;

import life.lxd.community.mapper.AdMapper;
import life.lxd.community.model.Ad;
import life.lxd.community.model.AdExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {
    @Autowired
    private AdMapper adMapper;

    public List<Ad> list(String pos){
        AdExample adExample = new AdExample();
        adExample.createCriteria()
                .andStatusEqualTo(1)
                .andPosEqualTo(pos)
                .andGmtStartLessThan(System.currentTimeMillis())
                .andGmtEndGreaterThan(System.currentTimeMillis());
//        adExample.setOrderByClause("priority desc");
        return adMapper.selectByExample(adExample);
    }
}
