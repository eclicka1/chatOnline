/**
 * 
 */
package chatonline.controller;

import chatonline.utility.AskFrd;
import chatonline.utility.Info;

/** @ClassName: HookListenClt 
 * @Description: get data from listener
 * @author Li 
 * @date 2012-4-22 ионГ01:45:22 
 * 
 */
public interface HookListenClt {
	public void delFrd(int aid);
	public void askForFrd(AskFrd aask);
	public void sentInfo(Info ainfo);
	public void getAskFrdRespond(int aid,boolean ais);
}
