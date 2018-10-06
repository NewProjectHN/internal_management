import React from 'react'
import propTypes from 'prop-types'
import styles from './css/component.css';
import Button from './Button';
import { connect } from 'react-redux';
import { Link } from "react-router-dom";

class DashboardNotificationComponent extends React.Component{

  constructor(props){
    super(props);
    this.state = {};
  }
  createLink(blockName){
    return blockName.toLowerCase();
  }

  render(){
    const {homeCount} = this.props;
    if(homeCount!==undefined){
      var {lastest,approving, approved, disapproved} = homeCount;
      var {need_approve1, need_approve2} = homeCount;
    }
    let dashboard;
    if(!this.props.text.includes('Leave')&&!this.props.text.includes('Overtime')){
      dashboard = 
                  <div className={"col-lg-12 col-md-12 col-xs-12 "+styles.panell}>
                    <div className={'panel panel-'+this.props.color +' '+styles.panel}>
                      <div className={"panel-heading "+styles.panel}>
                        <div className="row">
                        <Link to={`/pages/${this.createLink(this.props.blockName)}/${this.props.status}/${this.props.is_approved}`} className={styles.panel_footer}>
                          <div className="col-lg-7 col-md-7 col-xs-7">
                            <i className="fa fa-bell" style={{fontSize:'35px' ,color:'tomato'}}></i>
                            <span className={styles.text}>{this.props.text}</span>
                          </div>

                          <div className="col-lg-5 col-md-5 col-xs-5 text-right">
                            <div className={styles.count}>
                              {this.props.text==='New'&& lastest}
                              {this.props.text==='Approving'&& approving}
                              {this.props.text==='Approved'&& approved}
                              {this.props.text==='Disapproved'&& disapproved}
                            </div>
                              
                      
                          </div>
                          </Link>
                        </div>
                    </div>
                    </div>
            </div>
    }else{
      dashboard=<div className={"col-lg-12 col-md-12 col-xs-12 "+styles.panell}>
      <div className={'panel panel-'+this.props.color +' '+styles.panel}>
        <div className={"panel-heading "+styles.panel}>
          <div className="row">
          <Link to={`/approve/${this.createLink(this.props.blockName)}`} className={styles.panel_footer}>
            <div className="col-lg-5 col-md-5 col-xs-5">
              <i className="fa fa-bell" style={{fontSize:'35px' ,color:'tomato'}}></i>
              <span className={styles.text}>{this.props.text}</span>
            </div>
            <div className="col-lg-7 col-md-7 col-xs-7 text-right">
              <div className={styles.count}>
                {this.props.text==='Leave Approved'&& need_approve1}
                {this.props.text==='Overtime Approved'&& need_approve2}
              </div>
            </div>
            </Link>
          </div>
      </div>
      </div>
</div>
    }
    return(
        <div>
          {dashboard}
        </div>
    );
  }
}

DashboardNotificationComponent.propTypes = {
  color : propTypes.oneOf([
    'primary',
    'success',
    'info','warning',
    'danger'
  ]).isRequired,
  text : propTypes.string,
  onClick: propTypes.func,
  count: propTypes.object
}

DashboardNotificationComponent.defaultProps = {
  text: ''
}

// function mapStateToProps(state) {
//   const { homeCount} = state.homes;
  
//   return {
//       homeCount
//   };
// }
// export default connect(mapStateToProps)(DashboardNotificationComponent);
export default DashboardNotificationComponent;
