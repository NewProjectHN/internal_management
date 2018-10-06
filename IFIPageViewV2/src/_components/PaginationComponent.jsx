import React from 'react';
import classnames from 'classnames';
import styles from  './css/component.css';
import SelectListComponent from './SelectListComponent';

export default class Pagination extends React.Component{
    constructor(props){
        super(props);
        this.state = {};
        this.onPageChange = this.onPageChange.bind(this);
        this.goFirstPage = this.goFirstPage.bind(this);
        this.goLastPage = this.goLastPage.bind(this);
        this.goPrevPage = this.goPrevPage.bind(this);
        this.goNextPage = this.goNextPage.bind(this);
    }


    componentWillReceiveProps(newProps){
        if(newProps === this.props) return;
        const { margin, page, count } = newProps;
        const startPage = page > margin ? page - margin : 1;
        const endPage = page + margin > count ? count : page + margin;
        this.setState({ startPage, endPage, count });
    }

    onPageChange(event) {
        const index =
          Array.prototype.indexOf.call(event.target.parentNode.children, event.target);
          console.log(index);
        this.props.onPageChange(index + this.state.startPage);
      }
    
    goFirstPage() {
    this.props.onPageChange(1);
    }

    goLastPage() {
    this.props.onPageChange(this.state.count);
    }

    goPrevPage() {
    this.props.onPageChange(this.props.page - 1);
    }

    goNextPage() {
    this.props.onPageChange(this.props.page + 1);
    }
    onChange(){
        this.props.onChange();
    }


    render(){
        const {startPage, endPage, count} = this.state;
        const {page, margin,selectData} = this.props;
        const pages = [];
        const firstPage = page - margin > 1 ?
        <div className={styles.pagination_button +" "+ styles.pagination_go_first} onClick={this.goFirstPage}>
            1
        </div> :
        null;

        const lastPage = page + margin < count ? 
        <div className={styles.pagination_button + " "+ styles.pagination_go_last} onClick={this.goLastPage}>
            {count}
        </div> : 
        null;

        const nextPage = page == count ? null :
        <div className={styles.pagination_button} onClick={this.goNextPage}>
        &gt;
        </div>

        const prePage = page == 1 ? null : 
        <div className={styles.pagination_button} onClick={this.goPrevPage}>
        	&lt;
        </div>


        for (let i = startPage; i <= endPage; i++) {
            pages.push(
            <li
                key={i}
                onClick={this.onPageChange}
                className={styles.pagination_list_item+ " "+styles.pagination_button+" "+ (i === this.props.page ? styles.active : "") }
            >
                {i}
            </li>
            );
        }

        return(
            <div id={styles.pagination_container}>
                <div id={styles.pagination}>
                    {prePage}
                    {firstPage}
                    <ul id={styles.pagination_list}>
                        {pages}
                    </ul>
                    {lastPage}
                    {nextPage}
                </div>
            </div>
        );
    }
}