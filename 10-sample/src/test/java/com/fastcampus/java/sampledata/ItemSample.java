package com.fastcampus.java.sampledata;

import com.fastcampus.java.component.LoginUserAuditorAware;
import com.fastcampus.java.config.JpaConfig;
import com.fastcampus.java.model.entity.Category;
import com.fastcampus.java.model.entity.Item;
import com.fastcampus.java.model.entity.Partner;
import com.fastcampus.java.model.enumclass.ItemStatus;
import com.fastcampus.java.repository.CategoryRepository;
import com.fastcampus.java.repository.ItemRepository;
import com.fastcampus.java.repository.PartnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@DataJpaTest                                                                    // JPA 테스트 관련 컴포넌트만 Import
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 실제 db 사용
@DisplayName("ItemSample 생성")
@Import({JpaConfig.class, LoginUserAuditorAware.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ItemSample {

    private Random random = new Random();

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void createAll(){
        createHomeAppliance();
        createClothing();
        createMultiShop();
        createInterior();
        createFood();
        createSports();
        createShoppingMall();
        createDutyFree();
        createBeauty();
    }


    @Test
    public void createHomeAppliance(){
        String type = "COMPUTER";
        Category category = categoryRepository.findByType(type).get();
        List<Partner> partnerList = partnerRepository.findByCategory(category);

        for(Partner p : partnerList){

            for(int i = 1 ; i < 6; i ++){
                int div = (random.nextInt(10)+1) % 2;
                ItemStatus status = (div == 0 ? ItemStatus.REGISTERED : ItemStatus.UNREGISTERED);

                Item item = Item.builder()
                        .partner(p)
                        .status(status)
                        .name(category.getTitle()+i+"호")
                        .title(p.getName()+"의 가전제품"+i+"호")
                        .price(BigDecimal.valueOf((long)random.nextInt(999999)+1))
                        .content(p.getName()+"의 가전제품"+i+"호"+"의 가전제품 입니다. 2019년도 신제품 입니다")
                        .brandName(p.getName())
                        .registeredAt(getRandomDate())
                        .unregisteredAt(status.equals(ItemStatus.UNREGISTERED) ? getRandomDate() : null )
                        .build();

                itemRepository.save(item);
            }
        }
    }

    @Test
    public void createClothing(){

        String type = "CLOTHING";
        Category category = categoryRepository.findByType(type).get();
        List<Partner> partnerList = partnerRepository.findByCategory(category);

        for(Partner p : partnerList){

            for(int i = 1 ; i < 6; i ++){
                int div = (random.nextInt(10)+1) % 2;
                ItemStatus status = (div == 0 ? ItemStatus.REGISTERED : ItemStatus.UNREGISTERED);

                Item item = Item.builder()
                        .partner(p)
                        .status(status)
                        .name(category.getTitle()+i+"호")
                        .title(p.getName()+"의 의류제품"+i+"호")
                        .price(BigDecimal.valueOf((long)random.nextInt(999999)+1))
                        .content(p.getName()+"의 의류제품"+i+"호"+"의 겨울 상품 입니다. 2029년도 신제품 입니다")
                        .brandName(p.getName())
                        .registeredAt(getRandomDate())
                        .unregisteredAt(status.equals(ItemStatus.UNREGISTERED) ? getRandomDate() : null )
                        .build();
                itemRepository.save(item);
            }
        }
    }


    @Test
    public void createMultiShop(){

        String type = "MULTI_SHOP";
        Category category = categoryRepository.findByType(type).get();
        List<Partner> partnerList = partnerRepository.findByCategory(category);

        for(Partner p : partnerList){

            for(int i = 1 ; i < 6; i ++){
                int div = (random.nextInt(10)+1) % 2;
                ItemStatus status = (div == 0 ? ItemStatus.REGISTERED : ItemStatus.UNREGISTERED);

                Item item = Item.builder()
                        .partner(p)
                        .status(status)
                        .name(category.getTitle()+i+"호")
                        .title(p.getName()+"의 XX 제품"+i+"호")
                        .price(BigDecimal.valueOf((long)random.nextInt(999999)+1))
                        .content(p.getName()+"의 XX 제품"+i+"호"+"의 여러가지 상품 입니다. 2021년도 신제품 입니다")
                        .brandName(p.getName())
                        .registeredAt(getRandomDate())
                        .unregisteredAt(status.equals(ItemStatus.UNREGISTERED) ? getRandomDate() : null )
                        .build();
                itemRepository.save(item);
            }
        }
    }

    @Test
    public void createInterior(){

        String type = "INTERIOR";
        Category category = categoryRepository.findByType(type).get();
        List<Partner> partnerList = partnerRepository.findByCategory(category);

        for(Partner p : partnerList){

            for(int i = 1 ; i < 6; i ++){
                int div = (random.nextInt(10)+1) % 2;
                ItemStatus status = (div == 0 ? ItemStatus.REGISTERED : ItemStatus.UNREGISTERED);

                Item item = Item.builder()
                        .partner(p)
                        .status(status)
                        .name(category.getTitle()+i+"호")
                        .title(p.getName()+"의 가구"+i+"호")
                        .price(BigDecimal.valueOf((long)random.nextInt(999999)+1))
                        .content(p.getName()+"의 가구"+i+"호"+"의 원룸에 들어가는. 2023년도 신제품 입니다")
                        .brandName(p.getName())
                        .registeredAt(getRandomDate())
                        .unregisteredAt(status.equals(ItemStatus.UNREGISTERED) ? getRandomDate() : null )
                        .build();
                itemRepository.save(item);
            }
        }
    }

    @Test
    public void createFood(){

        String type = "FOOD";
        Category category = categoryRepository.findByType(type).get();
        List<Partner> partnerList = partnerRepository.findByCategory(category);

        for(Partner p : partnerList){

            for(int i = 1 ; i < 6; i ++){
                int div = (random.nextInt(10)+1) % 2;
                ItemStatus status = (div == 0 ? ItemStatus.REGISTERED : ItemStatus.UNREGISTERED);

                Item item = Item.builder()
                        .partner(p)
                        .status(status)
                        .name(category.getTitle()+i+"호")
                        .title(p.getName()+"의 제빵"+i+"호")
                        .price(BigDecimal.valueOf((long)random.nextInt(999999)+1))
                        .content(p.getName()+"의 제빵"+i+"호"+"의 생일 케이크 입니다")
                        .brandName(p.getName())
                        .registeredAt(getRandomDate())
                        .unregisteredAt(status.equals(ItemStatus.UNREGISTERED) ? getRandomDate() : null )
                        .build();
                itemRepository.save(item);
            }
        }
    }

    @Test
    public void createSports(){

        String type = "SPORTS";
        Category category = categoryRepository.findByType(type).get();
        List<Partner> partnerList = partnerRepository.findByCategory(category);

        for(Partner p : partnerList){

            for(int i = 1 ; i < 6; i ++){
                int div = (random.nextInt(10)+1) % 2;
                ItemStatus status = (div == 0 ? ItemStatus.REGISTERED : ItemStatus.UNREGISTERED);

                Item item = Item.builder()
                        .partner(p)
                        .status(status)
                        .name(category.getTitle()+i+"호")
                        .title(p.getName()+"의 겨울 스포츠"+i+"호")
                        .price(BigDecimal.valueOf((long)random.nextInt(999999)+1))
                        .content(p.getName()+"의 겨울 스포츠"+i+"호"+"의 스키 상품 입니다")
                        .brandName(p.getName())
                        .registeredAt(getRandomDate())
                        .unregisteredAt(status.equals(ItemStatus.UNREGISTERED) ? getRandomDate() : null )
                        .build();
                itemRepository.save(item);
            }
        }
    }

    @Test
    public void createShoppingMall(){

        String type = "SHOPPING_MALL";
        Category category = categoryRepository.findByType(type).get();
        List<Partner> partnerList = partnerRepository.findByCategory(category);

        for(Partner p : partnerList){

            for(int i = 1 ; i < 6; i ++){
                int div = (random.nextInt(10)+1) % 2;
                ItemStatus status = (div == 0 ? ItemStatus.REGISTERED : ItemStatus.UNREGISTERED);

                Item item = Item.builder()
                        .partner(p)
                        .status(status)
                        .name(category.getTitle()+i+"호")
                        .title(p.getName()+"의 OO 상품"+i+"호")
                        .price(BigDecimal.valueOf((long)random.nextInt(999999)+1))
                        .content(p.getName()+"의 OO상품"+i+"호"+"의 EE 상품 입니다")
                        .brandName(p.getName())
                        .registeredAt(getRandomDate())
                        .unregisteredAt(status.equals(ItemStatus.UNREGISTERED) ? getRandomDate() : null )
                        .build();
                itemRepository.save(item);
            }
        }
    }


    @Test
    public void createDutyFree(){

        String type = "DUTY_FREE";
        Category category = categoryRepository.findByType(type).get();
        List<Partner> partnerList = partnerRepository.findByCategory(category);

        for(Partner p : partnerList){

            for(int i = 1 ; i < 6; i ++){
                int div = (random.nextInt(10)+1) % 2;
                ItemStatus status = (div == 0 ? ItemStatus.REGISTERED : ItemStatus.UNREGISTERED);

                Item item = Item.builder()
                        .partner(p)
                        .status(status)
                        .name(category.getTitle()+i+"호")
                        .title(p.getName()+"의 면세상품"+i+"호")
                        .price(BigDecimal.valueOf((long)random.nextInt(999999)+1))
                        .content(p.getName()+"의 면세상품"+i+"호"+"의 면세 상품 입니다")
                        .brandName(p.getName())
                        .registeredAt(getRandomDate())
                        .unregisteredAt(status.equals(ItemStatus.UNREGISTERED) ? getRandomDate() : null )
                        .build();
                itemRepository.save(item);
            }
        }
    }


    @Test
    public void createBeauty(){

        String type = "BEAUTY";
        Category category = categoryRepository.findByType(type).get();
        List<Partner> partnerList = partnerRepository.findByCategory(category);

        for(Partner p : partnerList){

            for(int i = 1 ; i < 6; i ++){
                int div = (random.nextInt(10)+1) % 2;
                ItemStatus status = (div == 0 ? ItemStatus.REGISTERED : ItemStatus.UNREGISTERED);

                Item item = Item.builder()
                        .partner(p)
                        .status(status)
                        .name(category.getTitle()+i+"호")
                        .title(p.getName()+"의 OO 화장품"+i+"호")
                        .price(BigDecimal.valueOf((long)random.nextInt(99999)+1))
                        .content(p.getName()+"의 OO 화장품"+i+"호"+"의 상품 입니다")
                        .brandName(p.getName())
                        .registeredAt(getRandomDate())
                        .unregisteredAt(status.equals(ItemStatus.UNREGISTERED) ? getRandomDate() : null )
                        .build();
                itemRepository.save(item);
            }
        }
    }

    private LocalDateTime getRandomDate(){
        return LocalDateTime.of(2019,getRandomNumber(),getRandomNumber(),getRandomNumber(),getRandomNumber(),getRandomNumber());
    }

    private int getRandomNumber(){
        return random.nextInt(11)+1;
    }
}
