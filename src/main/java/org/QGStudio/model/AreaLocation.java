package org.QGStudio.model;


import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AreaLocation
 * @Description geohash块
 * @Author huange7
 * @Date 2019-08-10 22:47
 * @Version 1.0
 */
public class AreaLocation {

    private static final int GUANGZHOU = 0;

    private static final int HUADU = 1;

    private static final int NANSHA = 2;

    private static final int ZENGCHENG = 3;

    private static final int CONGHUA = 4;

    private static final int PANYU = 5;

    private static final int BAIYUN = 6;

    private static final int HUANGPU = 7;

    private static final int LIWAN = 8;

    private static final int HAIZHU = 9;

    private static final int TIANHE = 10;

    private static final int YUEXIU = 11;


    /**
     * @title : 天河区geohash5级块
     * @param :
     * @return :
     * @author : huange7
     * @date : 2019-08-10 22:56
     */
    public static final String[] TIANHE_DISTRICT = {"ws0eu", "ws0sh", "ws0s5", "ws0ev", "ws0sj", "ws0eg"};

    public static final String[] HUADU_DISTRICT = {"ws0kz", "ws0m6", "ws0my", "ws0t9", "ws0tg", "ws0mw",
            "ws0sc", "ws0kt", "ws0sf", "ws0te", "ws0mk", "ws0ts",
            "ws0m5", "ws0mp", "ws0mm", "ws0w4", "ws0m4",
            "ws0kq", "ws0t1", "ws0m7", "ws0t2", "ws0mt", "ws0w1",
            "ws0tf", "ws0tv", "ws0mq", "ws0t3", "ws0tt", "ws0mj", "ws0tc",
            "ws0kg", "ws0mr", "ws0w3", "ws0mz", "ws0mv", "ws0sb",
            "ws0km", "ws0t8", "ws0ku", "ws0tb", "ws0td", "ws0w6",
            "ws0mx", "ws0t6", "ws0kw", "ws0mh", "ws0kf", "ws0t4",
            "ws0kv", "ws0tu", "ws0t0", "ws0ky", "ws0mn"
    };

    public static final String[] ZENGCHENG_DISTRICT = {"ws0vs", "ws0ut", "ws0vw", "ws0v0", "ws0vy", "ws0ur", "ws0ym", "ws0gg",
            "ws0gy", "ws1h3", "ws1jb", "ws1hf", "ws0vr", "ws0v5", "ws0gb", "ws0yh", "ws0uk", "ws0vh", "ws1j3", "ws0uh",
            "ws0ud", "ws0uq", "ws0vv", "ws0vk", "ws0us", "ws0yq", "ws1j2", "ws0vx", "ws1h8", "ws0g9", "ws0v2", "ws0g8",
            "ws0u9", "ws0u5", "ws0uz", "ws0vz", "ws0v4", "ws0uj", "ws0u3", "ws0v6", "ws0uy", "ws0vu", "ws0un", "ws1j4",
            "ws0yn", "ws1hc", "ws0v3", "ws0ux", "ws0u1", "ws0uv", "ws0g3", "ws1h9", "ws0vt", "ws1h6", "ws1j6", "ws1j8",
            "ws0u6", "ws0g2", "ws1hd", "ws0u8", "ws0ug", "ws0u4", "ws0vm", "ws0v1", "ws1h2", "ws0gf", "ws0gu", "ws0gv",
            "ws0vq", "ws0uw", "ws0uc", "ws1j1", "ws0vj", "ws0yk", "ws0vp", "ws0gc", "ws0vn", "ws0uu", "ws1hb", "ws0u7",
            "ws0v7", "ws0u2", "ws0yj", "ws1j0", "ws0ub", "ws0ue", "ws0u0", "ws0um", "ws0uf"};

    // 广州市经纬度边界
    public static final Location MAX_GUANGZHOU = new Location().setLongitude(114.05474775).setLatitude(23.93551101);
    public static final Location MIN_GUANGZHOU = new Location().setLongitude(112.95341466).setLatitude(22.51735197);

    // 花都区经纬度边界
    public static final Location MAX_HUADU = new Location().setLongitude(113.47054624).setLatitude(23.61852136);
    public static final Location MIN_HUADU = new Location().setLongitude(112.95336713).setLatitude(23.25154012);

    // 南沙区经纬度边界
    public static final Location MAX_NANSHA = new Location().setLongitude(113.73527165).setLatitude(22.91117829);
    public static final Location MIN_NANSHA = new Location().setLongitude(113.29081476).setLatitude(22.51719596);

    // 增城区经纬度边界
    public static final Location MAX_ZENGCHENG = new Location().setLongitude(113.99645982).setLatitude(23.62246673);
    public static final Location MIN_ZENGCHENG = new Location().setLongitude(113.54261963).setLatitude(23.08702985);

    // 从化区经纬度边界
    public static final Location MAX_CONGHUA = new Location().setLongitude(114.05474775).setLatitude(23.93551101);
    public static final Location MIN_CONGHUA = new Location().setLongitude(113.27618446).setLatitude(23.37036426);

    // 番禺区经纬度边界
    public static final Location MAX_PANYU = new Location().setLongitude(113.57000643).setLatitude(23.08309105);
    public static final Location MIN_PANYU = new Location().setLongitude(113.24415718).setLatitude(22.85975588);

    // 白云区经纬度边界
    public static final Location MAX_BAIYUN = new Location().setLongitude(113.50487316).setLatitude(23.43128852);
    public static final Location MIN_BAIYUN = new Location().setLongitude(113.14452439).setLatitude(23.14058723);

    // 黄浦区经纬度边界
    public static final Location MAX_HUANGPU = new Location().setLongitude(113.60258444).setLatitude(23.41494668);
    public static final Location MIN_HUANGPU = new Location().setLongitude(113.39254983).setLatitude(23.03274906);

    // 荔湾区经纬度边界
    public static final Location MAX_LIWAN = new Location().setLongitude(113.27154412).setLatitude(23.16226802);
    public static final Location MIN_LIWAN = new Location().setLongitude(113.17172095).setLatitude(23.04449532);

    // 海珠区经纬度边界
    public static final Location MAX_HAIZHU = new Location().setLongitude(113.41419971).setLatitude(23.11622725);
    public static final Location MIN_HAIZHU = new Location().setLongitude(113.23675826).setLatitude(23.04635029);

    // 天河区经纬度边界
    public static final Location MAX_TIANHE = new Location().setLongitude(113.44256194).setLatitude(23.24837581);
    public static final Location MIN_TIANHE = new Location().setLongitude(113.29560448).setLatitude(23.10076866);

    // 越秀区经纬度边界
    public static final Location MAX_YUEXIU = new Location().setLongitude(113.31763552).setLatitude(23.17143379);
    public static final Location MIN_YUEXIU = new Location().setLongitude(113.23513573).setLatitude(23.10801373);

    public static final Map<Integer, String[]> AREA_MAP = new HashMap<>();
    public static final Map<Integer, Location> MAX_MAP = new HashMap<>();
    public static final Map<Integer, Location> MIN_MAP = new HashMap<>();

    static {
        MAX_MAP.put(GUANGZHOU,MAX_GUANGZHOU);
        MAX_MAP.put(HUADU,MAX_HUADU);
        MAX_MAP.put(NANSHA,MAX_NANSHA);
        MAX_MAP.put(ZENGCHENG,MAX_ZENGCHENG);
        MAX_MAP.put(CONGHUA,MAX_CONGHUA);
        MAX_MAP.put(PANYU,MAX_PANYU);
        MAX_MAP.put(BAIYUN,MAX_BAIYUN);
        MAX_MAP.put(HUANGPU,MAX_HUANGPU);
        MAX_MAP.put(LIWAN,MAX_LIWAN);
        MAX_MAP.put(HAIZHU,MAX_HAIZHU);
        MAX_MAP.put(TIANHE,MAX_TIANHE);
        MAX_MAP.put(YUEXIU,MAX_YUEXIU);
        MIN_MAP.put(GUANGZHOU,MIN_GUANGZHOU);
        MIN_MAP.put(HUADU,MIN_HUADU);
        MIN_MAP.put(NANSHA,MIN_NANSHA);
        MIN_MAP.put(ZENGCHENG,MIN_ZENGCHENG);
        MIN_MAP.put(CONGHUA,MIN_CONGHUA);
        MIN_MAP.put(PANYU,MIN_PANYU);
        MIN_MAP.put(BAIYUN,MIN_BAIYUN);
        MIN_MAP.put(HUANGPU,MIN_HUANGPU);
        MIN_MAP.put(LIWAN,MIN_LIWAN);
        MIN_MAP.put(HAIZHU,MIN_HAIZHU);
        MIN_MAP.put(TIANHE,MIN_TIANHE);
        MIN_MAP.put(YUEXIU,MIN_YUEXIU);
    }



}
