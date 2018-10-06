import React, { Component } from 'react';
import InputComponent from '../_components/InputComponent';
import Button from '../_components/Button';
import { userActions } from '../_actions/user.action';
import { connect } from 'react-redux';
import styles from './css/pages.css';

class Login extends React.Component{

    constructor(props) {
        super(props);
        this.state = {username: '',password:'',submitted: false};
    
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        const { name, value } = e.target;
        this.setState({ [name]: value });
    }

    handleSubmit(event){
        event.preventDefault();
        this.setState({ submitted: true });
        const { username, password } = this.state;
        const { dispatch } = this.props;
        if (username && password) {
        dispatch(userActions.login(username, password));
        }
    }
    render(){
        const { loggingIn } = this.props;
        const { username, password, submitted } = this.state;
        const styleButton = {
            height: "40px", color: "orange", width: "30%" ,fontsize: "20px" ,background:"darkgreen" ,borderRadius:"10px"
        };
        return(
            <form onSubmit={this.handleSubmit}>
                <div className="login_page">
                    <img src="assets/images/logo-ifi.png"/>
                    <div className="bottom_block" style={{backgroundImage: `url('assets/images/business-blue-and-white-backgrounds-powerpoint.jpg')`, backgroundSize:"cover"}}>                    <div><i className="fa fa-user logo_user" style={{lineHeight:0.8}}></i></div>
                        <h3 className="login_text">Sign In</h3>
                        <InputComponent name="username" type="text" placeholder="Username" icon="fa fa-user" value={this.username} onChange={this.handleChange}/><br/><br/>
                        {submitted && !username &&
                                    <div className={styles.input_error}>Username is required</div>
                        }
                        <InputComponent name="password" type="password" placeholder="Password" icon="fa fa-lock" value={this.password} onChange={this.handleChange}/>
                        {submitted && !password &&
                            <div className={styles.input_error}>Password is required</div>
                        }
                        <div className="button_block">
                            <Button type="submit" {...styleButton}>
                                Login
                            </Button>
                        </div>
                    </div>
                </div>
            </form>
        )
    }
}
function mapStateToProps(state) {
    const { loggingIn } = state.authentication;
    return {
        loggingIn
    };
}

const connectedLoginPage = connect(mapStateToProps)(Login);
export { connectedLoginPage as Login };