import React, { useState } from 'react';
import Navbar from './components/Navbar';
import DashboardPage from './pages/DashboardPage';
import ForecastingPage from './pages/ForecastingPage';
import PortsPage from './pages/PortsPage';
import MarketPage from './pages/MarketPage';
import WeatherPage from './pages/WeatherPage';
import OptimizationPage from './pages/OptimizationPage';
import AlertsPage from './pages/AlertsPage';

export default function App() {
  const [activeTab, setActiveTab] = useState('dashboard');

  const renderActivePage = () => {
    switch (activeTab) {
      case 'dashboard':
        return <DashboardPage />;
      case 'forecasting':
        return <ForecastingPage />;
      case 'ports':
        return <PortsPage />;
      case 'market':
        return <MarketPage />;
      case 'weather':
        return <WeatherPage />;
      case 'optimization':
        return <OptimizationPage />;
      case 'alerts':
        return <AlertsPage />;
      default:
        return <DashboardPage />;
    }
  };

  return (
    <div className="min-h-screen flex flex-col bg-slate-950 text-slate-100">
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} alertCount={4} />

      <main className="flex-1 max-w-7xl w-full mx-auto px-4 lg:px-8 py-8">
        {renderActivePage()}
      </main>

      <footer className="bg-slate-900/60 border-t border-slate-800/80 py-6 text-center text-xs font-mono text-slate-500">
        <div className="max-w-7xl mx-auto px-4 flex flex-col sm:flex-row items-center justify-between gap-3">
          <span>⚓ HEXAKADAL Commercial Decision Support System (v2.0 Architecture)</span>
          <span>Powered by Spring Boot 3, XGBoost4J, Google OR-Tools, Resilience4j &amp; React</span>
        </div>
      </footer>
    </div>
  );
}
