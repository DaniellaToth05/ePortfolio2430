// src/components/Dashboard/Dashboard.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Dashboard.css';

const Dashboard = () => {
  const navigate = useNavigate();

  const handleAddInvestmentClick = () => {
    navigate('/add-investment');
  };

  return (
    <div className="dashboard">
      <div className="greeting-section">
        <h2 className="greeting">Good Afternoon, Daniella</h2>
        <div className="Greeting-line"></div>
        <p className="agenda">What’s on your investment agenda today?</p>
      </div>

      <div className="button-section">
        <button className="dashboard-button" onClick={handleAddInvestmentClick}>
          Add Investment
        </button>
        <button className="dashboard-button">Sell Investment</button>
        <button className="dashboard-button">Update Prices</button>
        <button className="dashboard-button">View Gain</button>
        <button className="dashboard-button">Search Portfolio</button>
        <button className="dashboard-button">Set A Goal</button>
      </div>

      <div className="portfolio-summary">
        <div className="vertical-line"></div>
        <h3>Your Portfolio Summary:</h3>
        <div className="summary-details">
          <div className="detail">
            <p><strong>Total Value</strong></p>
            <div className="detail-line"></div>
            <p>$15,000</p>
          </div>
          <div className="detail">
            <p><strong>Top Investments</strong></p>
            <div className="detail-line"></div>
            <p>AAPL +15%</p>
            <p>TSLA +12%</p>
            <p>AMZN +10%</p>
          </div>
          <div className="detail">
            <p><strong>Number of Investments</strong></p>
            <div className="detail-line"></div>
            <p>8</p>
          </div>
          <div className="detail">
            <p><strong>Cash on Hand</strong></p>
            <div className="detail-line"></div>
            <p>$1,500</p>
          </div>
          <div className="detail">
            <p><strong>Goal Progress</strong></p>
            <div className="detail-line"></div>
            <p>Education Fund 75%</p>
            <p>Retirement Fund 88%</p>
          </div>
          <div className="detail">
            <p><strong>Investment Diversity</strong></p>
            <div className="detail-line"></div>
            <p>Stocks 70%</p>
            <p>Mutual Funds 30%</p>
          </div>
        </div>
      </div>

      <div className="dashboard-image">
        <img src="../../../src/assets/images/investmentGrowth.png" alt="Investment Growth" />
      </div>
    </div>
  );
};

export default Dashboard;
