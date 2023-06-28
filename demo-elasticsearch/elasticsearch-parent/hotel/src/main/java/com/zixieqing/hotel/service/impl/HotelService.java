package com.zixieqing.hotel.service.impl;

import com.zixieqing.hotel.mapper.HotelMapper;
import com.zixieqing.hotel.pojo.Hotel;
import com.zixieqing.hotel.service.IHotelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {
}
