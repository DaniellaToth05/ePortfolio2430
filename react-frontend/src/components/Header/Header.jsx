// src/components/Header.jsx
import React from 'react';
import './Header.css';
import logo from '../../assets/images/logo.png'; 

const Header = () => {
  return (
    <header className="header">
      <img src={logo} alt="Logo" className="header-logo" />
      <h1 className="header-title">ePORTFOLIO</h1>
    </header>
  );
};

export default Header;
