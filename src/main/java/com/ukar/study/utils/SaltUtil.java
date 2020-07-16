package com.ukar.study.utils;


import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jia.you
 * @date 2020/06/04
 */
@Slf4j
public class SaltUtil {
    private Map<Integer, String> saltMap;
    private int len;

    private static SaltUtil instance = new SaltUtil();

    private SaltUtil(){
       init();
    }

    public static SaltUtil getInstance(){
        return instance;
    }

    public String getSalt(String acctNo){
        int hash = Math.abs(acctNo.hashCode());
        String salt = saltMap.get(hash % len);
        log.info(String.format("账户号：%s，hash绝对值为：%d，盐值为：%s", acctNo, hash, salt));
        return salt;
    }

    private void init(){
        saltMap = new HashMap<>(16);
        saltMap.put(0, "i4UgWMb6Zj#EXk9-zDyH'2tVMPMAPg^ThDZSIYEs-u4F6?oDGJ-Mv62uQ7cl&Qcx9LBAzv?sLeu}keAf;xY5ON5ThO}agVaNcqZA?LbpZ.tkSg<HsFj3imK5%apJwgwq");
        saltMap.put(1, "XAxMnPL2Cr(z5tl%iUDI,125iYhsEj?0EGd9uhjC$QRRO>qzGR?MjdpeqfSg[dvHY0ofvZ*JlhZ`qJAu'W2T2XBvkn-S7BcvySKb_bk7Y,DZXa?T62e2GJWN.MVxkQDN");
        saltMap.put(2, "XAxMnPL2Cr(z5tl%iUDI,125iYhsEj?0EGd9uhjC$QRRO>qzGR?MjdpeqfSg[dvHY0ofvZ*JlhZ`qJAu'W2T2XBvkn-S7BcvySKb_bk7Y,DZXa?T62e2GJWN.MVxkQDN");
        saltMap.put(3, "TcOf3yk0Qt/eOku?FT5P?GTixybwXM?tP6Ty1KZn?0RIO>ZCnf<xEblpN1sd|nouQSNlIT[XSHa?80Qk*917bHbWy2?63bL13p5K}yKom'sBmV<3EjfsE8bx,4rBe6le");
        saltMap.put(4, "912zodH32b^z6Ha$UmHo<vPh1PSmNb,CG41N9xaE+v9WM<o4v7+8p20HW8Bk(2r1vxylVv>KaEL]f0E9|rISetubQ9<UUPxOa8Sg;ut0b*GPtQ?8gqD4aR3o?w8WezzG");
        saltMap.put(5, "M0gEvjHp33?PEYu?YzhG#PMkQd7TV3)aTIhYbNek.OuKH'tvBu!4vsLAWQEC+LejxaKaF5/vGxI?PZa8/fcHPYdNPP?6rrL8uCS4)hdRs`nQn1*zphekjxkY's6tDPfn");
        saltMap.put(6, "eoylZ95xvq#Vq9w+emJe?wArT4jaG7?yxD1iMACu?kmih]uVFt|ZG6dTw7ZP$QnrxfKPEj+fFib-2aMQ?DE63lTOXj.CmZfzwRvD]u383>Qnc1}KWswl7cuE[YUmGkT8");
        saltMap.put(7, "XU4ao74Rox%RD3w?OH8Y}h6Fr5Zntf#wijy5jOYx,UxD1?RNHn?7sDN0wf9a/9n1OLY4d2,DO7u)sTUk?r4BvvAUDH.2HfpaY8KW@vMFm?MRvV_VuHbyeCqt)T4c1lp1");
        saltMap.put(8, "1Pd9itWxwv%uAy2}QY6x{NZzPUEy4A>0sAbSxfvh;1jUh'xJ95?FAnqA302O$qhSEHvyff+0AKQ$lE47*6ztzv0jRU-8d9S19FBN;3Vvg$yg7b#Kyx8CM1UB?z8JP9kO");
        saltMap.put(9, "zEE8r6YsMm?8Zmx~Dvid?wmjntK36k/maXq7mS4Y|prEy;Y9M2$lWO8cKlcq]685W4j9Lp/6AH3(cpwp[7MMcycE9u.go1GwfaIm{zF41$ChyG}E8x8thLyj|NAAN9ye");
        saltMap.put(10, "33SfSJ8v2T+NKI7}fdY0?U7PGHYqL0?i4UDkAm2A%fMwu?iVQy!w8nB8Su9l-HgEeMbAbP>elJn.7jhO/rKY7RYlPK'Gis3Y3QPH?u3ic.Uikb?3ISoM67Ce'91C4C2I");
        saltMap.put(11, "EMzoQzt8f3>keeB?lmZx?KUZ1Dksy8(QxKzzStap(YOTa?XAsV.amci0VRji%WqHuBNboT}22Tv+0Tzh^b0KNNA1uS?iJZ3iMmMg_VjFo&FusU&w1vwONGmU?EpHV4th");
        saltMap.put(12, "ylPHR9ZchX)ur7C#O2hf%6McQX8s1O?mdtrzVYDk_nvau?1Rqc$wkBwwNBdQ-sNForJXev?eVgF?R6Zc'gwxEDRCM7*wt6vxldaf}Js1b$nmsN'xmcfOnk1A|FERwapX");
        saltMap.put(13, "n6leOXqSKl|ULsE,Z3VB}VqBqL9a3g,3zkSga2nj{9CD6.AaMQ@JJtxHveeK>SP90TvJa4?T1JZ|3poY?eKBZpNDVw{3yDKDk8XS{LXVK?9Yhg?aCsRYAViv?emxCxpE");
        saltMap.put(14, "F4VjAnuYLf%Whup?Nnjr?ppbghlMwp|MlQt7vzDh{C9bJ*oEMV$ov7ySz4RM?tqaPJvE7C?kzns-WfkQ.D0uMV7slx'BlhfpGQ36~BEN3?WnUV+yO1RSCPgv!CjGfNbu");
        saltMap.put(15, "S7XNWwciVz]wdF1?NRE4.Lf3IhQv0Q[47SYjriLV.uQk4/rpB9{W0NCy2PIl%L33p54uZO?iYj4&0NO1_FHQIP9tZL)eEErGR9TV{dHMq>PiGS]G4m3vMfDO?YMxu1l5");
        len = saltMap.size();
    }

    public static void main(String[] args) {
        String salt = SaltUtil.getInstance().getSalt("6666000001042121_B00155350");
        System.out.println(salt);
    }
}
