package contocorrenteweb;

import java.util.Observable;

public class ContoCorrente extends Observable
{

    private int money;

    public ContoCorrente(int money)
    {
	this.money = money;
    }

    public synchronized void bankDeposit(String money)
    {
	this.money += Integer.parseInt(money);
	refresh();
    }

    public synchronized void bankWithdrawal(String money)
    {
	if (this.money > 0 && this.money >= Integer.parseInt(money))
	{
	    this.money -= Integer.parseInt(money);
	    refresh();
	}
	else
	{
	    System.out.println("Soldi insufficienti sul conto ");
	}
    }

    public synchronized void refresh()
    {
	setChanged();
	notifyObservers();
    }

    public synchronized ContoCorrente getConto()
    {
	return this;
    }

    public synchronized int getSoldiConto()
    {
	return this.money;
    }
}
