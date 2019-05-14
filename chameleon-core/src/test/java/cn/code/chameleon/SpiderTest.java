package cn.code.chameleon;

import cn.code.chameleon.carrier.Results;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.processor.SimplePageProcessor;
import cn.code.chameleon.thread.ThreadUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liumingyu
 * @create 2018-04-17 下午8:34
 */
public class SpiderTest {

    private static final ObjectMapper OBJECT_MAPPER;

    private static ExecutorService executorService = ThreadUtils.createPoolWithFixQueue(100, 150, 1000, "test-%d");
    static {
        OBJECT_MAPPER = new ObjectMapper();
        //如果json中的key在目标对象中没有对应的setter方法, 是否抛出异常, 反序列化失败
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
    public static <K, V> Map<K, V> toMap(String jsonStr, Class<K> keyClass, Class<V> valueClass) throws IOException {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        return OBJECT_MAPPER.readValue(jsonStr, javaType);
    }

    @Ignore("long time")
    @Test
    public void testStartAndStop() throws InterruptedException {
        Spider spider = Spider.create(new SimplePageProcessor("http://www.oschina.net/*")).addPipeline((Results results, Task task) -> {
                    System.out.println(1);
                }
        ).thread(1).addUrls("http://www.oschina.net/");
        spider.start();
        System.out.println("开始");
        Thread.sleep(10000);
        spider.stop();
        System.out.println("暂停");
        Thread.sleep(10000);
        spider.start();
        System.out.println("再开始");
        Thread.sleep(10000);
    }

    @Test
    public void test() throws Exception {
        /*String test = "刘明宇],[17611030819  ,刘明宇]   ,   [15611031819,liumingyu,liumingyu01,   ,刘明宇],[14611030819,liumingyu02,刘明宇],[18611030819";
        List<String> list = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(test);
        List<String> misList = Lists.newArrayList();
        List<String> mobiles = Lists.newArrayList();
        for (String a : list) {

            if (a.contains("[")) {
                a = a.replaceAll("\\[", "");
            }
            if (a.contains("]")) {
                a = a.replaceAll("]", "");
            }
            if (checkMobile(a)) {
                mobiles.add(a);
                continue;
            }
            if (checkChinese(a)) {
                continue;
            }
            misList.add(a);
        }

        System.out.println("mis: ");
        for (String mis : misList) {
            System.out.println(mis);
        }

        System.out.println("手机号：");
        for (String mobile : mobiles) {
            System.out.println(mobile);
        }
*/
        /*String str = "[\"刘明宇\",\"17612345819\"]";
        System.out.println(convertContact(str));
        String temp = convertContact(str);
        if (temp.contains("-")) {
            String[] contactArr = temp.split("-");
            for (String contact : contactArr) {
                if (StringUtils.isEmpty(contact)) {
                    continue;
                }
                if (checkMobile(contact)) {
                    System.out.println("手机号" + contact);
                    break;
                }
            }
        }*/
        /*String email = "liumingyu04@meituan.com";
        System.out.println(checkEmail(email));*/
        List<String> list = new ArrayList<>();
        list.add("琚红艺");
        list.add("17326918451");

        //System.out.println(list.toString());
        //String mobile = "[\"琚红艺\",\"17326918451\"]";
        //System.out.println(convertContact(list.toString()));

        String str = "[\"宋鑫1\", \"17612345619\"]";
        str = convertContact(str);
        System.out.println(str);

        if (str.contains("-")) {
            String[] contactArr = str.split("-");
            for (String contact : contactArr) {
                if (StringUtils.isEmpty(contact)) {
                    continue;
                }
                if (checkMobile(contact)) {
                    System.out.println(contact);
                    break;
                }
            }
        }

        String content = String.format("工单%s的ask任务已流转,点击工单号查看详情", "1");
        System.out.println(content);
        //String str = "     ";
        //System.out.println(StringUtils.isBlank(str));
        //System.out.println(StringUtils.isEmpty(str));
        /*List<TestEnum> enums = Lists.newArrayList();
        enums.add(TestEnum.ELEPHANT);
        enums.add(TestEnum.MOBILE);
        System.out.println("是否包含短信：" + enums.contains(TestEnum.MOBILE));
        String fieldKey = "CaseExtend.312";
        System.out.println(fieldKey.substring(fieldKey.indexOf('.') + 1, fieldKey.length()));
        System.out.println(fieldKey);
        String a = null;
        System.out.println(StringUtils.isBlank(a));
*/

        /*String x = null;
        System.out.println("::" + String.valueOf(x));

        String params = "{\"bizParams\":{\"taskId\":3973},\"userInfo\":{\"cid\":1,\"passport\":\"liumingyu04@sankuai.info\",\"uid\":7045884,\"userId\":\"liumingyu04\"}}";
        Long taskId = null;
        Long operatorId = null;
        try {
            Map<String, Object> paramMap = toMap(params, String.class, Object.class);
            if (MapUtils.isEmpty(paramMap)) {
                System.out.println("paramMap为空");
            }
            Map<String, Object> bizParams = (Map<String, Object>) paramMap.get("bizParams");
            Map<String, Object> userInfo = (Map<String, Object>) paramMap.get("userInfo");

            if (MapUtils.isEmpty(bizParams) || MapUtils.isEmpty(userInfo)) {
                System.out.println("paramMap为空");
            }

            String taskIdStr = String.valueOf(bizParams.get("taskId"));
            taskId = StringUtils.isBlank(taskIdStr) ? null : Long.valueOf(taskIdStr);

            String operatorIdStr = String.valueOf(userInfo.get("userId"));
            operatorId = StringUtils.isBlank(operatorIdStr) ? null : Long.valueOf(operatorIdStr);
            System.out.println("taskId:" + taskId);
            System.out.println("operatorId:" + operatorId);
        } catch (Exception e) {
            System.out.println("报错了:" + e);
        }*/
        //System.out.println("taskId:" + taskId);
        //System.out.println("operatorId:" + operatorId);

        /*String str1 = "2019-04-12 00:02:31";
        String str2 = "2019-04-12 20:00:00";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTime beforeTime = new DateTime(sdf1.parse(str1));
        DateTime endTime = new DateTime(sdf2.parse(str2));
        int seconds = Seconds.secondsBetween(beforeTime, endTime).getSeconds();
        System.out.println(seconds);*/
/*
        String aa = "{\"extension\":\"\",\"body\":{\"text\":\"@所有人  \\n 移动工单-待办事项： \\n 测试预计处理时长 \\n处理地址: https://dpurl.sankuai.com/81J9a3C\",\"fontName\":\"\",\"bold\":false,\"fontSize\":0},\"appId\":1,\"fromUid\":30453875,\"messageType\":\"text\",\"messageId\":810823223125176320,\"passport\":\"transfer_v1_v2@sankuai.info\",\"cts\":1555069062587,\"toGuid\":64010072828}";
        Map<String, Object> map = toMap(aa, String.class, Object.class);
        System.out.println(map.toString());

        System.out.println(map.get("fromUid"));
        System.out.println(map.get("messageType"));
        System.out.println(map.get("messageId"));
        System.out.println((Long)(map.get("cts")));

        String comma = ":";
        String comma1 = ":";
        System.out.println(comma.equals(comma1));

        String misList = "liumingyu04,huangshizhe,liubixi,wangte";

        String[] split = misList.split(",");
        for (String sp : split) {
            System.out.println(sp);
        }

        List<String> splitList = new ArrayList<>(Arrays.asList(split));

        System.out.println(splitList.toString());
        List<String> queryList = splitList.parallelStream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());

        System.out.println(queryList.toString());

        System.out.println(String.join(",", queryList));

        Map<Integer, String> testMap = Maps.newHashMap();
        testMap.put(1, "111");
        testMap.put(3, "333");
        testMap.put(2, "222");

        Map<Integer, String> resultMap = Maps.newLinkedHashMap();
        testMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(e -> resultMap.put(e.getKey(), e.getValue()));

        System.out.println(resultMap.toString());

        List<Field> fields = Lists.newArrayList();
        Field aaa = new Field();
        aaa.setId("aaa");
        aaa.setName("aaa");
        Field bbb = new Field();
        bbb.setId("bbb");
        bbb.setName("bbb");
        fields.add(aaa);
        fields.add(bbb);

        convertObj(fields);

        System.out.println(fields.toString());*/

        long[] aa = new long[]{0xc3a5c85c97cb3127L};
        System.out.println(aa[0]);

        System.out.println(new Long(0xc3a5c85c97cb3127L));
    }

    public void convertObj(Object object) {
        List<Field> fields = (List<Field>) object;
        for (Field field: fields) {
            if (field.getId().equals("aaa")) {
                fields.remove(field);
            }
        }
        object = fields;
    }

    public Integer getI(Integer a) {
        try {
            Random random = new Random();
            int round = random.nextInt(1000);
            System.out.println(Thread.currentThread().getName() + "==" + round);
            Thread.sleep(round);
        } catch (Exception e) {

        }
        return a + 1;
    }

    public List<String> getList() {
        return Collections.EMPTY_LIST;
    }

    public boolean testField(Field field) {
        return field instanceof SelectField;
    }

    public int testStar(Double qpsParam, Double tp99Param, Double tp999Param) throws Exception {
        Map<String, List> starThresholdMap = INTERFACE_PERFORMANCE_THRESHOLD;
        Map<String, String> starPercentageMap = INTERFACE_PERFORMANCE_PERCENTAGE;

        BigDecimal qpsIndex, tp99Index, tp999Index;
        int star, qpsStar, tp99Star, tp999Star;

        String qpsPercentage = starPercentageMap.get("qps");
        qpsIndex = new BigDecimal(StringUtils.isBlank(qpsPercentage) ? "0.6" : qpsPercentage);
        String tp99Percentage = starPercentageMap.get("tp99");
        tp99Index = new BigDecimal(StringUtils.isBlank(tp99Percentage) ? "0.3" : tp99Percentage);
        String tp999Percentage = starPercentageMap.get("tp999");
        tp999Index = new BigDecimal(StringUtils.isBlank(tp999Percentage) ? "0.1" : tp999Percentage);

        qpsStar = 1;
        tp99Star = 1;
        tp999Star = 1;


        List<Integer> qpsList = starThresholdMap.get("qps");
        qpsList = CollectionUtils.isEmpty(qpsList) ? Lists.newArrayList() : qpsList;
        List<Integer> tp99List = starThresholdMap.get("tp99");
        tp99List = CollectionUtils.isEmpty(tp99List) ? Lists.newArrayList() : tp99List;
        List<Integer> tp999List = starThresholdMap.get("tp999");
        tp999List = CollectionUtils.isEmpty(tp999List) ? Lists.newArrayList() : tp999List;

        for (int i = 0; i < qpsList.size(); i++) {
            Integer qps = qpsList.get(i);
            if (Objects.isNull(qpsParam) || Objects.isNull(qps)) {
                break;
            }
            double difference = new BigDecimal(qpsParam).subtract(new BigDecimal(qps)).doubleValue();
            if (difference < 0) {
                break;
            }
            qpsStar = i + 1;
        }

        System.out.println("qpsStar: " + qpsStar);

        for (int i = tp99List.size() - 1; i >= 0; i--) {
            Integer tp99 = tp99List.get(i);
            if (Objects.isNull(tp99Param) || Objects.isNull(tp99)) {
                break;
            }
            double difference = new BigDecimal(tp99Param).subtract(new BigDecimal(tp99)).doubleValue();
            if (difference <= 0) {
                break;
            }
            tp99Star = i + 1;
        }

        System.out.println("tp99Star: " + tp99Star);

        for (int i = tp999List.size() - 1; i >= 0; i--) {
            Integer tp999 = tp999List.get(i);
            if (Objects.isNull(tp999Param) || Objects.isNull(tp999)) {
                break;
            }
            double difference = new BigDecimal(tp999Param).subtract(new BigDecimal(tp999)).doubleValue();
            if (difference <= 0) {
                break;
            }
            tp999Star = i + 1;
        }

        System.out.println("tp999Star: " + tp999Star);

        BigDecimal qpsResult = qpsIndex.multiply(new BigDecimal(qpsStar));
        System.out.println("qpsResult: " + qpsResult);
        BigDecimal tp99Result = tp99Index.multiply(new BigDecimal(tp99Star));
        System.out.println("tp99Result: " + tp99Result);
        BigDecimal tp999Result = tp999Index.multiply(new BigDecimal(tp999Star));
        System.out.println("tp999Result: " + tp999Result + ", tp999Index: " + tp999Index);
        BigDecimal total = qpsResult.add(tp99Result).add(tp999Result);

        System.out.println("total: " + total.floatValue());
        star = Math.round(total.floatValue());

        return star;
    }

    public static final Map<String, List> INTERFACE_PERFORMANCE_THRESHOLD;

    static {
        INTERFACE_PERFORMANCE_THRESHOLD = new HashMap<>(3);
        INTERFACE_PERFORMANCE_THRESHOLD.put("qps", Arrays.asList(0, 50, 200, 500, 1000));
        INTERFACE_PERFORMANCE_THRESHOLD.put("tp99", Arrays.asList(2000, 1000, 500, 200, 0));
        INTERFACE_PERFORMANCE_THRESHOLD.put("tp999", Arrays.asList(2400, 1500, 800, 300, 0));
    }

    public static final Map<String, String> INTERFACE_PERFORMANCE_PERCENTAGE;

    static {
        INTERFACE_PERFORMANCE_PERCENTAGE = new HashMap<>(3);
        INTERFACE_PERFORMANCE_PERCENTAGE.put("qps", "0.6");
        INTERFACE_PERFORMANCE_PERCENTAGE.put("tp99", "0.3");
        INTERFACE_PERFORMANCE_PERCENTAGE.put("tp999", "0.1");
    }

    public static <T> T toBean(String jsonStr, Class<T> targetClass) throws IOException {
        return OBJECT_MAPPER.readValue(jsonStr, targetClass);
    }

    public static JsonNode toJsonNode(String jsonStr) throws IOException {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }

        return OBJECT_MAPPER.readTree(jsonStr);
    }

    public static class Field {
        private String id;
        private String name;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toString() {
            return "id:" + id + ",name:" + name;
        }
    }

    public static class SelectField extends Field {

        private String id;
        private String name;
        private String desc;
        private Long applyId;
        public SelectField() {}

        public SelectField(String id, String name) {
            this.id = id;
            this.name = name;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setApplyId(Long applyId) {
            this.applyId = applyId;
        }
        public Long getApplyId() {
            return applyId;
        }
    }

    private void throwException() {
        String message = "sadfsadfasdf异常";
        try {
            throw new Exception(message);
        } catch (Exception e) {
            System.out.println("抛出异常e:" + e);
        }
    }

    public static <T> List<T> toList(String jsonStr, Class<T> itemClass) throws IOException {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, itemClass);
        return OBJECT_MAPPER.readValue(jsonStr, javaType);
    }

    public static String toJsonStr(Object obj) throws JsonProcessingException {
        if (obj == null) {
            return null;
        }

        return OBJECT_MAPPER.writeValueAsString(obj);
    }


    ThreadLocal<Boolean> isSend = new ThreadLocal<>();

    private void set(Boolean temp) {
        isSend.set(temp);
    }

    private Boolean get() {
        return isSend.get();
    }

    private void remove() {
        isSend.remove();
    }

    public enum TestEnum {
        ELEPHANT(1,"大象"),
        EMAIL(2, "邮件"),
        MOBILE(3, "短信"),
        ;

        private Integer code;
        private String desc;

        TestEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public static void throwTest() throws Exception {
        throw new Exception("12312");
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    private String convertContact(String value) {
        String[] contactArr = value.split(",");
        if (contactArr.length != 2) {
            return value;
        }
        if (StringUtils.isEmpty(contactArr[0]) || StringUtils.isEmpty(contactArr[1])) {
            return value;
        }
        String result;
        String contactOne = parseContact(contactArr[0]).trim();
        //System.out.println("0:" + contactArr[0]);
        //System.out.println("contactOne:" + contactOne);
        String contactTwo = parseContact(contactArr[1]).trim();
        //System.out.println("1:" + contactArr[1]);
        //System.out.println("contactTwo:" + contactTwo);
        //System.out.println(contactTwo);
        if (checkMobile(contactTwo)) {
            result = contactOne + "-" + contactTwo;
        } else {
            result = contactTwo + "-" +contactOne;
        }
        return result;
    }

    private String parseContact(String contact) {
        if (contact.contains("[")) {
            contact = contact.replaceAll("\\[", "");
        }
        if (contact.contains("]")) {
            contact = contact.replaceAll("]", "");
        }
        if (contact.contains("\"")) {
            contact = contact.replaceAll("\"", "");
        }
        return contact;
    }

    public boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex,mobile);
    }

    public boolean checkChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex,chinese);
    }

    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)*";
        return Pattern.matches(regex, email);
    }
}
