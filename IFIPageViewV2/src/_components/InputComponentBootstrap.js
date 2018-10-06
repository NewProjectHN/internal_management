import React from 'react';
import PropTypes from 'prop-types';

export default class InputComponentBootstrap extends React.Component{
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

        console.log(value);

      }

      render() {
        return (
          <div className="form-group">
            <input
              className="form-control"
              name={this.props.name}
              type={this.props.type}
              value={this.props.value}
              onChange={this.onChange}
              placeholder={this.props.placeholder}
              {...this.props.readonly ? 'readonly' : null}
            />
          </div>
        );
      }
}

InputComponentBootstrap.propTypes = {

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

  InputComponentBootstrap.defaultProps = {
    type: 'text',
    name: '',
    defaultValue: '',
    placeholder: '',
    style: null,
    fa_icon: ''
  };
