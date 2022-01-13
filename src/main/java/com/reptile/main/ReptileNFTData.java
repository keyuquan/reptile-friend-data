package com.reptile.main;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class ReptileNFTData {
    public static void main(String[] args) throws IOException {
        // 请求数据
        String url = "https://cn.etherscan.com/tokens-nft?p=";

        // 一共有多少页面
        Document doc = Jsoup.connect(url + "1").get();
        Integer page = Integer.valueOf(doc.getElementsByClass("font-weight-medium").get(1).text());

        // 爬取每个页面的数据
        for (int i = 0; i < page; i++) {
            Document document = Jsoup.connect(url + page).get();
            // 解析数据
            Element table = document.getElementById("tblResult").selectFirst("tbody");
            Elements trs = table.select("tr");
            for (int j = 0; j < trs.size(); ++j) {
                Element tr = trs.get(j);
                Elements tds = tr.select("td");

                String id = tds.get(0).text();
                Element element = tds.get(1).getElementsByClass("text-primary").get(0);
                String token = element.attr("title");
                String name = element.text();
                String Transfers24H = tds.get(2).text();
                String Transfers3D = tds.get(3).text();

                NFTEntity nFTEntity = new NFTEntity();
                nFTEntity.setId(id);
                nFTEntity.setToken(token);
                 nFTEntity.setName(name);
                nFTEntity.setTransfers24H(Transfers24H);
                nFTEntity.setTransfers3D(Transfers3D);

                System.out.println(JSONObject.toJSONString(nFTEntity));

            }


        }


    }

    @Data
    static class NFTEntity {
        String id ;
        String token ;
        String name ;
        String Transfers24H ;
        String Transfers3D ;
    }

}

