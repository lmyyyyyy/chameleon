package cn.code.chameleon.spider.processor;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.model.SpiderTemplate;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.selector.Html;
import cn.code.chameleon.spider.enums.PatternTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-05-12 下午9:06
 */
public class CommonPageProcessor implements PageProcessor {

    private Site site;

    private SpiderTemplate spiderTemplate;

    public CommonPageProcessor(SpiderTemplate spiderTemplate) {
        site = Site.init()
                .setCycleRetryTimes(spiderTemplate.getRetryTimes())
                .setSleepTime(spiderTemplate.getSleepTime())
                .setTimeOut(spiderTemplate.getTimeout())
                .setDomain(spiderTemplate.getDomain())
                .setCharset(spiderTemplate.getCharset())
                .setObeyRobots(spiderTemplate.isObeyRobot())
                .setHeaders(spiderTemplate.getHeaders())
                .setCookies(spiderTemplate.getCookies());

        this.spiderTemplate = spiderTemplate;
    }

    @Override
    public void process(Page page) {
        if (spiderTemplate.getJumpUrls() != null && !spiderTemplate.getJumpUrls().isEmpty()) {
            for (String jumpUrl : spiderTemplate.getJumpUrls()) {
                if (page.getRequest().getUrl().matches(jumpUrl)) {
                    page.setJump(true);
                }
            }
        }
        if (spiderTemplate.getTargetUrls() != null && !spiderTemplate.getTargetUrls().isEmpty()) {
            for (SpiderTemplate.TargetUrl targetUrl : spiderTemplate.getTargetUrls()) {
                if (targetUrl.getTargetUrlType() == PatternTypeEnum.REGEX.getCode()) {
                    page.addTargetRequests(page.getHtml().links().regex(targetUrl.getTargetUrlPattern()).all());
                } else if (targetUrl.getTargetUrlType() == PatternTypeEnum.XPATH.getCode()) {
                    page.addTargetRequests(page.getHtml().links().xpath(targetUrl.getTargetUrlPattern()).all());
                }
            }
        }
        if (spiderTemplate.isCollection()) {
            SpiderTemplate.CollectionField collectionField = spiderTemplate.getCollectionField();
            PatternTypeEnum patternTypeEnum = PatternTypeEnum.getPatternTypeEnum(collectionField.getCollectionType());
            List<String> temps = null;
            List<Map<String, Object>> results = new ArrayList<>();
            switch (patternTypeEnum) {
                case REGEX:
                    if (collectionField.getRegex() == null || "".equals(collectionField.getRegex())) {
                        break;
                    }
                    temps = page.getHtml().regex(collectionField.getRegex()).all();
                    break;
                case XPATH:
                    if (collectionField.getXpath() == null || "".equals(collectionField.getXpath())) {
                        break;
                    }
                    temps = page.getHtml().xpath(collectionField.getXpath()).all();
                    break;
                case CSS:
                    if (collectionField.getCss() == null || "".equals(collectionField.getCss()) || collectionField.getProperty() == null || "".equals(collectionField.getProperty())) {
                        break;
                    }
                    temps = page.getHtml().$(collectionField.getCss(), collectionField.getProperty()).all();
                    break;
                case JSON_PATH:
                    if (collectionField.getJsonPath() == null || "".equals(collectionField.getJsonPath())) {
                        break;
                    }
                    temps = page.getHtml().jsonPath(collectionField.getJsonPath()).all();
                    break;
                default:
                    break;
            }
            for (String temp : temps) {
                Html html = new Html(temp);
                Map<String, Object> result = fillPage(page, html, spiderTemplate);
                if (result == null || result.isEmpty()) {
                    continue;
                }
                results.add(result);
            }
            page.putField("results", results);
        } else {
            fillPage(page, null, spiderTemplate);
        }
    }

    private Map<String, Object> fillPage(Page page, Html html, SpiderTemplate template) {
        if (template.getDynamicFields() != null && !template.getDynamicFields().isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            for (SpiderTemplate.DynamicField dynamicField : template.getDynamicFields()) {
                String result = null;
                if (dynamicField.getKey() == null || "".equals(dynamicField.getKey())) {
                    continue;
                }
                if (dynamicField.getType() == PatternTypeEnum.REGEX.getCode()) {
                    if (dynamicField.getRegex() == null || "".equals(dynamicField.getRegex())) {
                        continue;
                    }
                    if (html != null) {
                        result = html.regex(dynamicField.getRegex()).get();
                    } else {
                        result = page.getHtml().regex(dynamicField.getRegex()).get();
                    }
                } else if (dynamicField.getType() == PatternTypeEnum.XPATH.getCode()) {
                    if (dynamicField.getXpath() == null || "".equals(dynamicField.getXpath())) {
                        continue;
                    }
                    if (html != null) {
                        result = html.xpath(dynamicField.getXpath()).get();
                    } else {
                        result = page.getHtml().xpath(dynamicField.getXpath()).get();
                    }
                } else if (dynamicField.getType() == PatternTypeEnum.CSS.getCode()) {
                    if (dynamicField.getCss() == null || "".equals(dynamicField.getCss()) || dynamicField.getProperty() == null || "".equals(dynamicField.getProperty())) {
                        continue;
                    }
                    if (html != null) {
                        result = html.$(dynamicField.getCss(), dynamicField.getProperty()).get();
                    } else {
                        result = page.getHtml().$(dynamicField.getCss(), dynamicField.getProperty()).get();
                    }
                } else if (dynamicField.getType() == PatternTypeEnum.JSON_PATH.getCode()) {
                    if (dynamicField.getJsonPath() == null || "".equals(dynamicField.getJsonPath())) {
                        continue;
                    }
                    if (html != null) {
                        result = html.jsonPath(dynamicField.getJsonPath()).get();
                    } else {
                        result = page.getHtml().jsonPath(dynamicField.getJsonPath()).get();
                    }
                }
                if (html == null) {
                    page.putField(dynamicField.getKey(), result);
                } else {
                    map.put(dynamicField.getKey(), result);
                }
            }
            return map;
        }
        return null;
    }

    @Override
    public Site getSite() {
        return site;
    }
}
