import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import '../hover.css'

class HeaderComponent extends Component {
    render() {
        return (
            <div>
                <nav className='navbar navbar-expand-lg navbar-dark bg-dark'>
                    <Link to='/' className='navbar-brand mx-3'>Election Result Checker</Link>
                </nav>
            </div>
        );
    }
}

export default HeaderComponent;
