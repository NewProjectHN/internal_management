import React, { Component } from 'react';
import { BrowserRouter, Link, NavLink } from "react-router-dom";
import style from "./css/custom.css";
import $ from 'jquery';

export default class NavBar extends React.Component{
    constructor(props){
        super(props);
        this.state={
            showManager:true
        }
    }

    componentDidMount() {
           /* let user = JSON.parse(localStorage.getItem('user'));
            if(user.username.role_id==5)
                this.setState({showManager:true});
            else
                this.setState({showManager:false});*/
            $('#sidebar-menu').find('a').on('click', function(ev) {
            console.log('clicked - sidebar_menu');
            var $li = $(this).parent();

            if ($li.is('.active')) {

                // $('#sidebar-menu').find( "li" ).removeClass( "active active-sm" );
                $li.removeClass('active active-sm');
                $('ul:first', $li).slideUp(function() {
                    $('.right_col').css('min-height', $(window).height());

                    var bodyHeight = $('body').outerHeight(),
                        footerHeight = $('body').hasClass('footer_fixed') ? -10 : $('footer').height(),
                        leftColHeight = $('.left_col').eq(1).height() + $('.sidebar-footer').height(),
                        contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;

                    // normalize content
                    contentHeight -= $('.nav_menu').height() + footerHeight;

                    $('.right_col').css('min-height', contentHeight);
                });
            } else {

                // prevent closing menu if we are on child menu
                $('.child_menu').find( "li" ).removeClass( "active active-sm" );
                if (!$li.parent().is('.child_menu')) {
                    $('#sidebar-menu').find('li').removeClass('active active-sm');
                    $('#sidebar-menu').find('li ul').slideUp();
                }else
                {
                    if ( $('body').is( ".nav-sm" ) )
                    {
                        $('#sidebar-menu').find( "li" ).removeClass( "active active-sm" );
                        $('#sidebar-menu').find( "li ul" ).slideUp();
                    }
                }
                $li.addClass('active');

                $('ul:first', $li).slideDown(function() {
                    $('.right_col').css('min-height', $(window).height());

                    var bodyHeight = $('body').outerHeight(),
                        footerHeight = $('body').hasClass('footer_fixed') ? -10 : $('footer').height(),
                        leftColHeight = $('.left_col').eq(1).height() + $('.sidebar-footer').height(),
                        contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;

                    // normalize content
                    contentHeight -= $('.nav_menu').height() + footerHeight;

                    $('.right_col').css('min-height', contentHeight);
                });
            }




            // Set content height
            // console.log(window.location.href.split('#')[0].split('?')[0]);
            // $('#sidebar-menu').find('a[href="' + window.location.href.split('#')[0].split('?')[0] + '"]').parent('li').addClass('current-page');

            // $('#sidebar-menu').find('a').filter(function () {
            //     return this.href == window.location.href.split('#')[0].split('?')[0];
            // }).parent('li').addClass('current-page').parents('ul').slideDown(function() {
            //     $('.right_col').css('min-height', $(window).height());

            //     var bodyHeight = $('body').outerHeight(),
            //         footerHeight = $('body').hasClass('footer_fixed') ? -10 : $('footer').height(),
            //         leftColHeight = $('.left_col').eq(1).height() + $('.sidebar-footer').height(),
            //         contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;

            //     // normalize content
            //     contentHeight -= $('.nav_menu').height() + footerHeight;

            //     $('.right_col').css('min-height', contentHeight);
            // }).parent().addClass('active');

            // // recompute content when resizing

            // $('.right_col').css('min-height', $(window).height());

            // var bodyHeight = $('body').outerHeight(),
            //     footerHeight = $('body').hasClass('footer_fixed') ? -10 : $('footer').height(),
            //     leftColHeight = $('.left_col').eq(1).height() + $('.sidebar-footer').height(),
            //     contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;

            // // normalize content
            // contentHeight -= $('.nav_menu').height() + footerHeight;

            // $('.right_col').css('min-height', contentHeight);

            // // fixed sidebar
            // if ($.fn.mCustomScrollbar) {
            //     $('.menu_fixed').mCustomScrollbar({
            //         autoHideScrollbar: true,
            //         theme: 'minimal',
            //         mouseWheel:{ preventDefault: true }
            //     });
            // }
        });

    }
    setContentHeight = function () {
        // reset height
        $('.right_col').css('min-height', $(window).height());

        var bodyHeight = $('body').outerHeight(),
            footerHeight = $('body').hasClass('footer_fixed') ? -10 : $('footer').height(),
            leftColHeight = $('.left_col').eq(1).height() + $('.sidebar-footer').height(),
            contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;

        // normalize content
        contentHeight -= $('.nav_menu').height() + footerHeight;

        $('.right_col').css('min-height', contentHeight);
    };


    render(){
      //  let {showManager}=this.state;
        let user = JSON.parse(localStorage.getItem('user'));

        return(
            <div className="col-md-3 left_col">
          <div className="left_col scroll-view">
            <div className="navbar nav_title" style={{border: 0}}>

                <a  className="site_title"><i className="fa fa-paw"></i> <span>IFI SOLUTION</span></a>

            </div>

            <div className="clearfix"></div>

            {/* <!-- menu profile quick info --> */}
            <div className="profile clearfix">
              <div className="profile_pic">
                <img src="assets/images/img.jpg" alt="..." className="img-circle profile_img img-responsive"/>
              </div>
              <div className="profile_info">
                <span>Welcome,</span>
                <h2>{user != undefined ? user.username.fullname : ""}</h2>
              </div>
            </div>
            {/* <!-- /menu profile quick info --> */}

            <br /><br/>

            {/* <!-- sidebar menu --> */}
            <div id="sidebar-menu" className="main_menu_side hidden-print main_menu">
              <div className="menu_section">
                <h3>Menu</h3>

                    <ul className="nav side-menu">
                        <li><NavLink to="/home"><i className="fa fa-home"></i>Home</NavLink>
                        </li>

                        <li><a><i className="fa fa-table"></i> My Pages <span className="fa fa-chevron-down"></span></a>
                            <ul className="nav child_menu animated rubberBand">
                            <li><NavLink activeClassName={style.activeClass } to="/pages/leave">My Leave</NavLink></li>
                            <li><NavLink activeClassName={style.activeClass} to="/pages/overtime">My Overtime</NavLink></li>
                            </ul>
                        </li>
                        {user && user.username.role_id !=5 &&
                            <li><a><i className="fa fa-check-square-o"></i> Approve<span className="fa fa-chevron-down"></span></a>
                                <ul className="nav child_menu">
                                <li><NavLink activeClassName={style.activeClass}  to="/approve/leave">Approve Leave</NavLink></li>
                                <li><NavLink activeClassName={style.activeClass} to="/approve/overtime">Approve Overtime</NavLink></li>
                                </ul>
                            </li>
                        }
                         {/*{user && user.username.role_id !=5 &&*/}
                             {/*<li><a><i className="fa fa-tasks"></i>Allocation<span className="fa fa-chevron-down"></span></a>*/}
                             {/*<ul className="nav child_menu">*/}

                                 {/*<li><NavLink activeClassName={style.activeClass} to="/pages/employee">Employee</NavLink></li>*/}
                                 {/*<li><NavLink activeClassName={style.activeClass} to="/pages/project">Project</NavLink></li>*/}

                             {/*</ul>*/}
                         {/*</li>*/}
                         {/*}*/}

                    </ul>

              </div>
            </div>
            {/* <!-- /sidebar menu --> */}
          </div>
        </div>
        );
    }
}
