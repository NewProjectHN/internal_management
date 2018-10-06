import React from 'react';
import PropTypes from 'prop-types';

class SelectListComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
        this.handleChange = this.handleChange.bind(this);
    }

    // Được gọi khi người dùng click vào option
    handleChange(value) {
        this.setState({value})
        console.log(this.refs.sl.value);
    }

    render() {
        return (

            <div className="form-group custom-gap">
                <select ref="sl" className="form-control" id="sel1" onChange={this.props.onChange}
                        style={this.props.style} value={this.props.valueSelect} name={this.props.name}>
                    { this.props.isAddEmpty &&
                        <option key="" value=""
                            selected={(this.props.option == null || this.props.selectName == '') === this.props.selectName ? 'selected' : ''}></option>
                    }
                    {this.props.option != null ? this.props.option.map((option, index) => {
                        return <option key={index} value={option.value}
                                       selected={option.name === this.props.selectName ? 'selected' : ''}>{option.name}</option>
                    }) : ""}
                </select>
            </div>

        );
    }
}

SelectListComponent.propTypes = {
    onChange: PropTypes.func,
    name: PropTypes.string.isRequired,
    option: PropTypes.array
}

SelectListComponent.defaultProps = {
    name: '',
    option: null
}

export default SelectListComponent;
