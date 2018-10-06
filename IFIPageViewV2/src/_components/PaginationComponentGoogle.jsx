import React from 'react';
import SelectListComponent from './SelectListComponent';
import styles from './css/component.css';


export default class Pagination extends React.Component {
    constructor(props) {
        super(props);
        this.state = { 
            pager: {},
            selectData:[
                {
                    "name":"5",
                    "value":5
                },
                {
                    "name":"10",
                    "value":10
                },
                {
                    "name":"20",
                    "value":20
                },
                {
                    "name":"30",
                    "value":30
                }
            ],
            sizePerPage: 10,
            page:1
         };
        this.changePageSize = this.changePageSize.bind(this);
        // this.setPage = this.setPage.bind(this);
    }
    componentDidMount() {
        
        this.setPage(this.state.page,this.state.sizePerPage);
        
    }

    setPage(page) {
        var items = this.props.items;
        var pages = this.props.pages;
        var pagerNew = this.state.pager;
        

        if (page < 1 || page > pages) {
            return;
        }

        // get new pager object for specified page
        pagerNew = this.getPager(pages, page ,this.state.sizePerPage);


        // update state
        this.setState({ pager: pagerNew });
        console.log(items);


        if(page===undefined) page = this.state.page;
        this.props.onChangePage(page,this.state.sizePerPage);
    }

    getPager(pagesTotal, currentPage, pageSize) {
        // default to first page
        currentPage = currentPage || 1;

        // default page size is 10
        pageSize = pageSize || 10;

        // calculate total pages
        var totalPages = pagesTotal;

        var startPage, endPage;
        if (totalPages <= pageSize) {
            // less than 10 total pages so show all
            startPage = 1;
            endPage = totalPages;
        } else {
            // more than 10 total pages so calculate start and end pages
            if (currentPage <= 6) {
                startPage = 1;
                endPage = 10;
            } else if (currentPage + 4 >= totalPages) {
                startPage = totalPages - 9;
                endPage = totalPages;
            } else {
                startPage = currentPage - 5;
                endPage = currentPage + 4;
            }
        }

        // calculate start and end item indexes
        var startIndex = (currentPage - 1) * pageSize;
        var endIndex = Math.min(startIndex + pageSize - 1, pagesTotal - 1);

        // create an array of pages to ng-repeat in the pager control
        var pages = pagesTotal;

        // return object with all pager properties required by the view
        return {
            currentPage: currentPage,
            pageSize: pageSize,
            pages: pages
        };
    }
    changePageSize(e){
        var items = this.props.items;
        var pages = this.props.pages;
        var pager = this.state.pager;
        // get new pager object for specified page
        pager = this.getPager(pages, 1 ,Number(e.target.value));

        this.setState({
            pager: pager,
            sizePerPage: Number(e.target.value)
        });
        // call change page function in parent component
        this.props.onChangePage(1,Number(e.target.value));
        
    }

    render() {
        var pager = this.state.pager;

        if (!this.props.pages || this.props.pages.length <= 1) {
            // don't display pager if there is only 1 page
            return null;
        }

        
        let pageTmp = [];
        for(let i = 0 ;i < this.props.pages;i++){
            pageTmp.push(i+1)
        }
        console.log("render paging");

        return (
            
            <div class="custom-paging">
            <ul className="pagination">
                <li className={pager.currentPage === 1 ? 'disabled' : ''}>
                    <a onClick={() => this.setPage(1)}>&lt;&lt;</a>
                </li>
                <li className={pager.currentPage === 1 ? 'disabled' : ''}>
                    <a onClick={() => this.setPage(pager.currentPage - 1)}>&lt;</a>
                </li>
                {pageTmp && pageTmp.map((page, index) =>
                    <li key={index} className={pager.currentPage === page ? 'active' : ''}>
                        <a onClick={() => this.setPage(page)}>{page}</a>
                    </li>
                )}
                <li className={pager.currentPage === this.props.pages ? 'disabled' : ''}>
                    <a onClick={() => this.setPage(pager.currentPage + 1)}>&gt;</a>
                </li>
                <li className={pager.currentPage === this.props.pages ? 'disabled' : ''}>
                    <a onClick={() => this.setPage(this.props.pages)}>&gt;&gt;</a>
                </li>
            </ul>

            <SelectListComponent option={this.state.selectData} onChange={this.changePageSize} className={styles.pageSize2} valueSelect={this.state.sizePerPage}/>
            </div>
        );
    }
}
