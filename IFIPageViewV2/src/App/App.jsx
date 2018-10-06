import React from 'react';
import { Router, Route } from 'react-router';
import { connect } from 'react-redux';
import DatePicker from 'react-datepicker';
import styles from './css/style.css';
import InputComponent from '../_components/InputComponent';
import Button from '../_components/Button';
import ReactConfirmAlert, { confirmAlert } from 'react-confirm-alert';

import DatePickerComponent from '../_components/DatePickerComponent';

import BlockDashboardNotificationComponent from '../_components/BlockDashboardNotificationComponent';


import TableHeaderComponent from '../_components/TableHeaderComponent';
import TableBody from '../_components/TableBody';

import BlockDashboard from '../_components/BlockDashboardNotificationComponent';
import SelectListComponent from '../_components/SelectListComponent';
import DateTimeComponent from '../_components/DateTimeComponent';

import NavBar from '../TemplatePage/NavBar';
import Header from '../TemplatePage/Header';
import Footer from '../TemplatePage/Footer';
import { history } from '../_helpers/history.js';
import { alertActions } from '../_actions/alert.actions.js';
import {mypageActions} from '../_actions/mypage.actions.js';

import './css/custom.js';
import {Home} from '../Pages/Home';
import {MyLeave} from '../Pages/MyLeave';
import {MyOvertime} from '../Pages/MyOvertime';

import {Login} from '../Pages/Login';
import $ from 'jquery';
import {ApproveLeave} from '../Pages/ApproveLeave';
import { ApproveOvertime } from '../Pages/ApproveOvertime';
import {AddEmployee} from '../Pages/AddEmployee';

import { PrivateRoute } from "../_components/PrivateRoute";

class App extends React.Component {

    constructor(props){

        //history.push("/login");


        super(props);
        this.state = {

            rows: [],
            columns: [],
            clicked: false
        };
        $('#alert_fade').fadeIn(1000).delay(2000).fadeOut(2000);
        const { dispatch } = this.props;
        history.listen((location, action) => {
            // clear alert on location change
            dispatch(alertActions.clear());
        });
        this.handleLogin=this.handleLogin.bind(this);
    }

    handleLogin(){
        this.props.dispatch(mypageActions.openPopup());
    };

    render() {

        const {isOpen,alert} = this.props;

        const basePath = '/' + window.location.pathname.split('/')[1];
        console.log(basePath);

        const styleButton = {
            height: "40px", color: "yellow", width: "10%" ,fontSize: "20px" ,background:"green" ,borderradius:"10px"
        };
        const styleHeaderTable={
            height: "40px", color: "#ffff", width: "100px", fontSize: "15px", background: "#23527C"
        };


        return (
            <div>
                {alert.message &&
                    <div className={`alert ${alert.type} alert-custom`}>{alert.message}</div>
                }
                <Router history={history}>
                    <div>
                            <Route path="/login"  component={Login} />
                            <PrivateRoute exact path="/home"  component={Home} />
                            <PrivateRoute exact path="/"  component={Home} />
                            <PrivateRoute exact path="/pages/leave" component={MyLeave} />
                            <PrivateRoute exact path="/pages/leave/:status" component={MyLeave} />
                            <PrivateRoute exact path="/pages/leave/:status/:is_approved" component={MyLeave}/>
                            <PrivateRoute path="/approve/leave" component={ApproveLeave} />
                            <PrivateRoute exact path="/pages/overtime" component={MyOvertime} />
                            <PrivateRoute exact path="/pages/overtime/:status" component={MyOvertime} />
                            <PrivateRoute exact path="/pages/overtime/:status/:is_approved" component={MyOvertime} />
                            <PrivateRoute exact path="/approve/overtime" component={ApproveOvertime} />
                            {/*<PrivateRoute exact path="/pages/employee" component={EmployeeManager}/>*/}
                            {/*<PrivateRoute exact path="/pages/project" component={ProjectManager}/>*/}
                            {/*<PrivateRoute exact path="/pages/allocation/:project_id" component={Allocation} />*/}
                            {/*<PrivateRoute exact path="/pages/addemployee/:project_id" component={AddEmployee} />*/}
                    </div>
				</Router>
			</div>


        );
    }
}

function mapStateToProps(state) {
    const { alert } = state;
    const { isOpen } = state.popup;
    return {
        alert,
        isOpen,
    };
}


const connectedApp = connect(mapStateToProps)(App);
export { connectedApp as App };
