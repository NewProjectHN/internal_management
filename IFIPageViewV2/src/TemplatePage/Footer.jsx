import React, { Component } from 'react';
import NavBar from './NavBar';
import Header from './Header';
import { Router, Route } from 'react-router';

export default class Footer extends React.Component{
    render(){
        return(
            
            // <!-- footer content -->
            <footer style={{background: "#2A3F54", color:"aliceblue", marginTop: "27px"}}>
                <div className="col-md-4">
                    IFI SOLUTION
                    <hr/>
                    IFI Solution chuyên thực hiện các dự án phần mềm cho 
                    các khách hàng Pháp, Italia, Nhật Bản trong các lĩnh vực công nghệ như Master Data Management,
                    IoT, Embedded Software, Mobile Apps
                </div>
                <div className="col-md-4">
                    QUICK LINK
                    <hr/>
                    <a href="#" style={{color: "aliceblue"}}>Tuyển LTV Web UI</a><br/>
                    <a href="#" style={{color: "aliceblue"}}>Tuyển Comtor</a><br/>
                    <a href="#" style={{color: "aliceblue"}}>Tuyển JAVA TEAM LEADER</a><br/>
                    <a href="#" style={{color: "aliceblue"}}>Tuyển LTV JAVA</a>
                </div>
                <div className="col-md-4">
                    GET IN TOUCH
                    <hr/>
                    <i className="fa fa-home"><span>&nbsp;&nbsp;&nbsp;F12, Thang Long Tower 98A Nguy Nhu Kon Tum, HaNoi, VietNam</span></i><br/>
                    <i className="fa fa-phone">&nbsp;&nbsp;&nbsp;+84(0)2462512890</i><br/>
                    <i className="fa fa fa-envelope-o">&nbsp;&nbsp;&nbsp;contact@ifisolution.com</i>
                </div>
                <div className="clearfix"></div>
            </footer>
            // <!-- /footer content -->
        )
    }
}