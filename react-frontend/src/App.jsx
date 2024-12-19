// src/App.jsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Dashboard from './components/Dashboard/Dashboard';
import Header from './components/Header/Header';
import AddInvestment from './components/Add-Investment/AddInvestment'; // Correct import for AddInvestment component

function App() {
  return (
    <Router>
      <>
        <Header />
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/add-investment" element={<AddInvestment />} />
        </Routes>
      </>
    </Router>
  );
}

export default App;
