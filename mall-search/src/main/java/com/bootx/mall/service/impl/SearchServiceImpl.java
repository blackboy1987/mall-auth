
package com.bootx.mall.service.impl;

import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.config.ElasticSearchConfig;
import com.bootx.mall.entity.Product;
import com.bootx.mall.entity.Store;
import com.bootx.mall.entity.vo.SkuVo;
import com.bootx.mall.service.SearchService;
import com.bootx.mall.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service - 搜索
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class SearchServiceImpl implements SearchService {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private RestHighLevelClient restHighLevelClient;

	@Override
	public void index(Class<?> type) {
		index(type, true);
	}

	@Override
	public void index(Class<?> type, boolean purgeAll) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<SkuVo> search(String keyword, Product.Type type, Store.Type storeType, Store store, Boolean isOutOfStock, Boolean isStockAlert, BigDecimal startPrice, BigDecimal endPrice, Product.OrderType orderType, Pageable pageable) {
		if (StringUtils.isEmpty(keyword)) {
			return Page.emptyPage(pageable);
		}

		if (pageable == null) {
			pageable = new Pageable();
		}

		SearchRequest searchRequest = new SearchRequest("product");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchQuery("name",keyword));
		if(type!=null){
			boolQueryBuilder.filter(QueryBuilders.termQuery("type",type));
		}
		if(storeType!=null){
			boolQueryBuilder.filter(QueryBuilders.termQuery("storeType",storeType));
		}
		if(store!=null&&store.getId()!=null){
			boolQueryBuilder.filter(QueryBuilders.termQuery("storeId",store.getName()));
		}

		if(startPrice!=null||endPrice!=null){
			RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price");
			if(startPrice!=null){
				rangeQueryBuilder.gte(startPrice);
			}
			if(endPrice!=null){
				rangeQueryBuilder.lte(endPrice);
			}
			boolQueryBuilder.filter(rangeQueryBuilder);
		}


		if(isOutOfStock!=null){
			boolQueryBuilder.filter(QueryBuilders.termQuery("hasStock",!isOutOfStock));
		}
		searchSourceBuilder.query(boolQueryBuilder);

		searchSourceBuilder.from((pageable.getPageNumber()-1)*pageable.getPageSize());
		searchSourceBuilder.size(pageable.getPageSize());
		searchRequest.source(searchSourceBuilder);
		try{
			SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHits = hits.getHits();
			List<SkuVo> skuVos = new ArrayList<>();
			for (SearchHit hit : searchHits) {
				System.out.println(hit.getId());
				skuVos.add(JsonUtils.toObject(hit.getSourceAsString(),SkuVo.class));
			}
			return new Page<>(skuVos,hits.getTotalHits().value,pageable);
		}catch (Exception e){
			e.printStackTrace();
		}

		return Page.emptyPage(pageable);
	}

}