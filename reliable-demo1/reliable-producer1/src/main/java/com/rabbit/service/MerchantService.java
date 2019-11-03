package com.rabbit.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbit.config.AmqpSend;
import com.rabbit.entity.CommandType;
import com.rabbit.entity.Merchant;
import com.rabbit.mapper.MerchantMapper;
import com.rabbit.util.RabbitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.util.List;

/**
 * @author gyc
 * @date 2019/11/2
 */
@Service
@Transactional
@Slf4j
public class MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;


    @Autowired
    private RabbitUtil rabbitUtil;

    public List<Merchant> getMerchantList() {
       return  merchantMapper.selectList(null);
    }


    public int add(Merchant merchant) {
        int insert = merchantMapper.insert(merchant);
        try {
            rabbitUtil.send(merchant,merchant.getId() + "",CommandType.ADD);
        }catch (Exception e){
            log.error(e.toString(),e);
            throw  new RuntimeException("rabbit发送失败");
        }
        return insert;

    }

    @AmqpSend(CommandType.DELETE)
    public int delete(Integer id) {
        return merchantMapper.deleteById(id);
    }

    public Merchant getMerchantById(Integer id) {
        return merchantMapper.selectById(id);
    }


    public int update(Merchant merchant) {

        int num = merchantMapper.updateById(merchant);
        try {
            rabbitUtil.send(merchant,merchant.getId() + "",CommandType.UPDATEBYID);
        }catch (Exception e){
            log.error(e.toString(),e);
            throw  new RuntimeException("rabbit发送失败");
        }
        return num;
    }


    @AmqpSend(CommandType.UPDATE_CONDTION)
    public int updateState(Merchant merchant) {
        UpdateWrapper<Merchant> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",merchant.getId());
        return merchantMapper.update(merchant,wrapper);
    }
}
