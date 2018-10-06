import React, {Component} from 'react';
import {connect} from 'react-redux';
import NavBar from '../TemplatePage/NavBar';
import Header from '../TemplatePage/Header';
import {Router, Route} from 'react-router';
import BlockDashboardNotificationComponent from '../_components/BlockDashboardNotificationComponent';
import styles from './css/pages.css';
import {leaveActions} from '../_actions/leave.actions.js';
import {homeActions} from '../_actions/home.actions.js';

import 'react-confirm-alert/src/react-confirm-alert.css' // Import css
import {mypageActions} from '../_actions/mypage.actions';

class Home extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            users: [],
            renderedUsers: [],
            loading: true,
            pages: 0,
            page: 1,
            sizePerPage: 10,
            status: 0,
            rows: [],
            pageOfItems: []
        };
        this.handlePageChange = this.handlePageChange.bind(this);
        this.onChangePage = this.onChangePage.bind(this);
        this.changePerPage = this.changePerPage.bind(this);
        this.openAdd = this.openAdd.bind(this);

    }

    componentDidMount() {
        this.fetchData();
    }

    fetchData() {
        this.props.dispatch(leaveActions.getLeaveByPage());
        this.props.dispatch(homeActions.getCount());
    }

    handlePageChange(page) {
        const renderedUsers = this.state.users.slice((page - 1) * this.state.sizePerPage, (page - 1) * this.state.sizePerPage + this.state.sizePerPage);
        // in a real app you could query the specific page from a server user list
        this.setState({page, renderedUsers});
    }

    changePerPage(e) {
        this.setState({
            sizePerPage: Number(e.target.value)
        });
        console.log(Number(e.target.value));
    }

    onChangePage(pageOfItems) {
        this.setState({pageOfItems: pageOfItems});
        console.log(pageOfItems);
    }


    openAdd() {
        this.props.dispatch(mypageActions.openPopup());
        console.log(this.props.homeCount);
    }

    render() {
        const {leaveList, pages, loading, isOpen} = this.props;
        const {page, total, renderedUsers, pageOfItems, users} = this.state;

        const {homeCount} = this.props;
        let user = JSON.parse(localStorage.getItem('user'));

        return (
            <div className="col-lg-12 col-md-12 col-xs-12 custom-padding-home">
                <NavBar/>
                <Header/>
                {user && user.username.role_id !== 5 &&
                    <div className="right_col">
                        <div className="row">
                            <div className="col-lg-6 col-md-12 col-xs-12">
                                <div className={styles.dashboad}>
                                    <BlockDashboardNotificationComponent collapseId="tt" text="Leave"
                                                                         homeCount={homeCount}/>
                                </div>
                            </div>
                            <div className="col-lg-6 col-md-12 col-xs-12">
                                <div className={styles.dashboad}>
                                    <BlockDashboardNotificationComponent collapseId="ttt" text="Manager"/>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-lg-6 col-md-12 col-xs-12">
                                <div className={styles.dashboad}>
                                    <BlockDashboardNotificationComponent collapseId="tttt" text="Overtime"/>
                                </div>
                            </div>
                        </div>
                    </div>
                }
                {user && user.username.role_id === 5 &&
                    <div className="right_col">
                        <div className="row">
                            <div className="col-lg-6 col-md-12 col-xs-12">
                                <div className={styles.dashboad}>
                                    <BlockDashboardNotificationComponent collapseId="tt" text="Leave"
                                                                         homeCount={homeCount}/>
                                </div>
                            </div>
                            <div className="col-lg-6 col-md-12 col-xs-12">
                                <div className={styles.dashboad}>
                                    <BlockDashboardNotificationComponent collapseId="tttt" text="Overtime"/>
                                </div>
                            </div>
                        </div>
                    </div>
                }
            </div>
        )
    }
}

function mapStateToProps(state) {
    const {isOpen} = state.popup;
    const {homeCount, loading} = state.homes;
    console.log(homeCount);
    console.log(isOpen);
    return {
        homeCount,
        loading,
        isOpen
    };
}

const connectedLeave = connect(mapStateToProps)(Home);
export {connectedLeave as Home};
