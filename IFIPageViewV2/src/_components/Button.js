import React, { Component } from 'react';
import PropTypes from 'prop-types';



class Button extends Component {
  constructor(props){
      super(props);
      this.state = {};
      this.onClick = this.onClick.bind(this);
  }

  onClick(){
    if(this.props.onCick){
      this.props.onClick();
      console.log('clicked');
    }else{
      console.log('kakaka');
    }
  }
  render() {
    const{type,btn,children} = this.props;
    return (   
      <button type={type} className={"btn btn-"+this.props.btn} onClick={this.props.onClick} disabled={this.props.disabled}>{children}</button>
    );
  }
}
Button.propTypes = {
  type: PropTypes.oneOf([
    'submit',
    'reset',
    'button'
  ]).isRequired,
  btn:PropTypes.oneOf([
    'primary',
    'info',
    'success',
    'warning',
    'danger',
    'default',
    'link',
    'btn'
  ]),
  onClick: PropTypes.func,
}

Button.defaultProps={
  type:''

}
export default Button;
