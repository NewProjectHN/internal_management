import React from 'react'
import DashboardNotificationComponent from '../_components/DashboardNotificationComponent';
import PropTypes from 'prop-types';
import styles from './css/component.css';
import Button from '../_components/Button';
import { homeActions } from '../_actions/home.actions';
import { connect } from 'react-redux';

class BlockDashboardNotificationComponent extends React.Component{
  constructor(props){
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      items: []
    };
    this.icon = true;
    this.clickToCollapse = this.clickToCollapse.bind(this);
    this.closeOpenCollapse = this.closeOpenCollapse.bind(this);
  }
  
  componentDidMount(){
    const count = this.props.homeCount;
    // console.log(count);
  }
  
  clickToCollapse(event){
    this.icon = !this.icon;
    this.forceUpdate();
    let block = document.getElementById("tt");

    this.closeOpenCollapse(event);
    
    
    if(this.props.text==='Leave'){
      this.props.dispatch(homeActions.getCount());
      return this.props.homeLeaveCount;
      
    }else if(this.props.text==='Overtime'){
      this.props.dispatch(homeActions.getOvertimeCount());
      return this.props.homeOvertimeCount;
      
    }else if(this.props.text==='Manager'){
      this.props.dispatch(homeActions.getCountNeedApproveManager());
      return this.props.homeManagerCount;
    }
  }

  closeOpenCollapse(event){
    let arrayblock = document.getElementsByClassName("panel-collapse");
    let arraytitleblock = document.getElementsByClassName("title-block");
    
    if(event.target.nodeName==='I'){
      if(event.target.className ==="fa fa-angle-down pull-right"){
        for(let i = 0;i < arrayblock.length;i++){
          if(arrayblock[i].className==="panel-collapse collapse in"){
            arrayblock[i].className ="panel-collapse collapse";
            arraytitleblock[i].getElementsByTagName("i")[0].setAttribute("className", "fa fa-angle-down pull-right");
            arraytitleblock[i].getElementsByTagName("i")[0].className = "fa fa-angle-down pull-right";
          
            console.log(arraytitleblock[i].getElementsByTagName("i")[0].className);
            console.log(i);
          }
        }
      }
    }else if(event.target.nodeName==='DIV'){
      if(event.target.getElementsByTagName('i')[0].className ==="fa fa-angle-down pull-right"){
        for(let i = 0;i < arrayblock.length;i++){
          if(arrayblock[i].className==="panel-collapse collapse in"){
            arrayblock[i].className ="panel-collapse collapse";
            arraytitleblock[i].getElementsByTagName("i")[0].setAttribute("className", "fa fa-angle-down pull-right");
            arraytitleblock[i].getElementsByTagName("i")[0].className = "fa fa-angle-down pull-right";
          
            console.log(arraytitleblock[i].getElementsByTagName("i")[0].className);
            console.log(i);
          }
        }
    }
  }else if(event.target.nodeName==='STRONG'){
    if(event.target.parentElement.getElementsByTagName('i')[0].className ==="fa fa-angle-down pull-right"){
      for(let i = 0;i < arrayblock.length;i++){
        if(arrayblock[i].className==="panel-collapse collapse in"){
          arrayblock[i].className ="panel-collapse collapse";
          arraytitleblock[i].getElementsByTagName("i")[0].setAttribute("className", "fa fa-angle-down pull-right");
          arraytitleblock[i].getElementsByTagName("i")[0].className = "fa fa-angle-down pull-right";
        
          console.log(arraytitleblock[i].getElementsByTagName("i")[0].className);
          console.log(i);
        }
      }
    } 
  }
  }
  render(){
    let homeCount;
    if(this.props.homeLeaveCount!==undefined){
      homeCount = this.props.homeLeaveCount;
    }else if(this.props.homeOvertimeCount!==undefined){
      homeCount = this.props.homeOvertimeCount;
    }else if(this.props.homeManagerCount!==undefined){
      homeCount = this.props.homeManagerCount;
    }
    let block;
    if(!this.props.text.includes('Manager')){
      block =   <div className={' col-md-12 col-xs-12 '+styles.block_dashboard }>
                    <div className="title-block col-lg-12 col-md-12 col-xs-12" onClick={this.clickToCollapse} /*className={"accordion_toggle "+styles.accordion_toggle+' '+styles.collapsed} class="accordion_toggle "*/ data-toggle="collapse" data-target={"#"+this.props.collapseId}>
                      <strong onClick={this.clickToCollapse} className={"accordion_toggle "+styles.accordion_toggle+' '+styles.collapsed} /*class="accordion_toggle "*/ data-toggle="collapse" data-target={"#"+this.props.collapseId} style={{fontSize:'40px',color:'#339966'}}>{this.props.text}</strong>

                      
                      <i data-toggle="collapse" data-target={"#"+this.props.collapseId} onClick={this.clickToCollapse} className={!this.icon?'fa fa-angle-right pull-right':'fa fa-angle-down pull-right'} style={{fontSize:"48px",color:"orange"}}></i>
                    </div>

                    <div id={this.props.collapseId} className={"panel-collapse collapse"}>
                        
                        <DashboardNotificationComponent blockName = {this.props.text} is_approved='' status='1' text="New" color="primary" homeCount={homeCount}/>

                        <DashboardNotificationComponent blockName = {this.props.text}  is_approved='false' status='2' text="Approving" color="warning" homeCount={homeCount}/>

                        <DashboardNotificationComponent blockName = {this.props.text} text="Approved" is_approved='true' status='2,3,4,5,6,7,8,9,10' color="success" homeCount={homeCount}/>

                        <DashboardNotificationComponent blockName = {this.props.text} text="Disapproved" is_approved='false' status='-1' color="danger" homeCount={homeCount}/>
                        
                    </div>
                  </div>
    }else{
      block = <div className={' col-md-12 col-xs-12 '+styles.block_dashboard }>
                  <div className="title-block col-lg-12 col-md-12 col-xs-12" onClick={this.clickToCollapse} /*className={"accordion_toggle "+styles.accordion_toggle+' '+styles.collapsed} class="accordion_toggle "*/ data-toggle="collapse" data-target={"#"+this.props.collapseId}>
                    <strong onClick={this.clickToCollapse} className={"accordion_toggle "+styles.accordion_toggle+' '+styles.collapsed} /*class="accordion_toggle "*/ data-toggle="collapse" data-target={"#"+this.props.collapseId} style={{fontSize:'40px',color:'#339966'}}>{this.props.text}</strong>

                    
                    <i data-toggle="collapse" data-target={"#"+this.props.collapseId} onClick={this.clickToCollapse} className={!this.icon?'fa fa-angle-right pull-right':'fa fa-angle-down pull-right'} style={{fontSize:"48px",color:"orange"}}></i>
                  </div>

                  <div id={this.props.collapseId} className={"panel-collapse collapse"}>
                      
                      <DashboardNotificationComponent blockName = 'leave' is_approved='' status='1' text="Leave Approved" color="primary" homeCount={homeCount}/>

                      <DashboardNotificationComponent blockName = 'overtime'  is_approved='false' status='2' text="Overtime Approved" color="warning" homeCount={homeCount}/>
                  </div>
                </div>
    }
    return(
      <div>{block}</div>
    );
  }
}

BlockDashboardNotificationComponent.propTypes = {
  text:PropTypes.string.isRequired,
  homeCount:PropTypes.object
}




function mapStateToProps(state) {
  const { homeLeaveCount, homeOvertimeCount, homeManagerCount} = state.homes;
  console.log(homeLeaveCount+"-"+homeOvertimeCount+"-"+homeManagerCount);
  return {
    homeLeaveCount,
    homeOvertimeCount,
    homeManagerCount
  };
}
export default connect(mapStateToProps)(BlockDashboardNotificationComponent);
