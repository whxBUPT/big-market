package bupt.whx.trigger.api;

import bupt.whx.trigger.api.dto.RaffleAwardListRequestDTO;
import bupt.whx.trigger.api.dto.RaffleAwardListResponseDTO;
import bupt.whx.trigger.api.dto.RaffleRequestDTO;
import bupt.whx.trigger.api.dto.RaffleResponseDTO;
import bupt.whx.types.model.Response;

import java.util.List;

/**
 * ClassName:IRaffleService
 * Package:bupt.whx.trigger.api
 * Description:       抽奖服务接口
 *
 * @Author whx
 * @Create 2024/3/16 21:00
 * @Version 1.0
 */
public interface IRaffleService {

    /**
     * 策略装配接口
     * @param strategyId
     * @return
     */
    Response<Boolean> strategyArmory(Long strategyId);


    /**
     * 查询奖品列表的接口
     * @param raffleAwardListRequestDTO
     * @return
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO raffleAwardListRequestDTO);

    /**
     * 随机抽奖接口
     * @param raffleRequestDTO
     * @return
     */
    Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO raffleRequestDTO);



}
