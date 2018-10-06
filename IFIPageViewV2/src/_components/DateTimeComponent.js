import React from 'react';
import styles from './css/component.css';
import Datetime from 'react-datetime';

export default class DateTimeComponent extends React.Component {
  

render(){
  var date = new Date();
    return(
      <div>
      <Datetime value={this.props.value}/>
      </div>
    );
}
 
  
}