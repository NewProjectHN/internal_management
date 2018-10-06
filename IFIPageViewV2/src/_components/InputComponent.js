import React from 'react';
import PropTypes from 'prop-types';
import styles from './css/component.css';

export default class InputComponent extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
          value: props.defaultValue,
        };
        this.onChange = this.onChange.bind(this);
      }

      componentDidMount() {

      }

      onChange(e) {
        const value = e.target.value;
        this.setState({ value });

        if (this.props.onChange) {
          this.props.onChange(e, this.props.name);
        }
      }

      render() {
        const textIcon = "textIndent: 32px";
        const textNon = "textIndent: 10px";
        return (
          <div className={styles.field_input}>
          {
            this.props.icon && 
            <span icon={this.props.fa_icon} className={this.props.icon} style={styles_custom.icon_custom}></span>
          }
            
            <input
              className={this.props.icon ? styles.text_indent : styles.text_none}
              name={this.props.name}
              type={this.props.type}
              value={this.state.value}
               onChange={this.onChange}
              placeholder={this.props.placeholder}
            />
          </div>
        );
      }
}
let styles_custom = {
  icon_custom: {
    position: 'absolute',
    top: '10px',
    left: '10px'
  }
};


InputComponent.propTypes = {

    type: PropTypes.oneOf([
      'text',
      'password',
      'email',
      'number',
      'checkbox'
    ]),

    fa_icon: PropTypes.string,

    name: PropTypes.string,

    name: PropTypes.string.isRequired,

    defaultValue: PropTypes.string,

    placeholder: PropTypes.string,

    onChange: PropTypes.func,

    style: PropTypes.shape({
      container: PropTypes.object,
      input: PropTypes.object
    }),

  };

  InputComponent.defaultProps = {
    type: 'text',
    name: '',
    defaultValue: '',
    placeholder: '',
    style: null,
    fa_icon: ''
  };
