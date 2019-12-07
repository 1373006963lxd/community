package life.lxd.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codedrinker on 2019/12/14.
 * 分页所承载的属性
 */
@Data
public class PaginationDTO<T> {
    //分页中所有的数据
    private List<T> data;
    //是否展示前一页
    private boolean showPrevious;
    //是否展示首页
    private boolean showFirstPage;
    //是否展示下一页
    private boolean showNext;
    //是否展示最后一页
    private boolean showEndPage;
    //当前页
    private Integer page;
    //显示页数集合
    private List<Integer> pages = new ArrayList<>();
    //总页数
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage=totalPage;
        this.page = page;
        //边界值处理
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }


        //存放前端页面需要展示的页码
        pages.add(page);
        //保证左边和右边都显示3页
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }

            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        // 是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        // 是否展示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        // 是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        // 是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
