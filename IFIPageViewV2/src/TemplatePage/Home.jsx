import React, { Component } from 'react';
import NavBar from '../TemplatePage/NavBar';
import Header from '../TemplatePage/Header';
import { Router, Route } from 'react-router';
import BlockDashboard from '../_components/BlockDashboardNotificationComponent';

export default class Home extends React.Component{
    render(){
        return(
            
            <div>
                <NavBar/>
                <Header/>
                    
                    <div className="right_col" role="main">
                        
                            <BlockDashboard text="Leave"/>
                        
                    </div>
            </div>
        )
    }
}