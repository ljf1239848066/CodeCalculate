using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;
using System.Diagnostics;
using System.Threading;

namespace CodeCalculate
{
    public partial class MainForm : Form
    {
        private List<string> pathList = new List<string>();
        private List<int> paraCount = new List<int>();
        private List<int> wordCount = new List<int>();
        private int totalCount = 0;
        private int count = 0;
        private string[] strExtension = new string[] { ".cs", ".java", ".cpp", ".aspx", ".xml", ".sql", ".html", 
            ".htm", ".c", ".h", ".m", ".config",".js" ,".xaml",".css"};
        private string[] extraExtension = new string[] { ".designer.cs",".Designer.cs", "R.java", "AssemblyInfo.cs","Program.cs" ,"AndroidManifest.xml"};//待排除的自动生成的文件格式
        private string[] extraWord = new string[] {"android\\support\\v4" };
        private List<string> myExtension=new List<string>();
        private Thread thread;
        private string strPath;
        public void calculate(string path)
        {
            if (!this.IsDisposed)
            {
                try
                {
                    string[] strDirectory = Directory.GetDirectories(path);//获取目录
                    string[] strFile = Directory.GetFiles(path);//获取文件名
                    foreach (string directory in strDirectory)
                    {
                        calculate(directory);
                    }
                    foreach (string file in strFile)
                    {
                        try
                        {
                            FileInfo fileinfo = new FileInfo(file);
                            string fileextension = fileinfo.Extension;
                            ListViewItem item;
                            if (isCalculate(fileextension) && isEliminate(file))
                            {
                                count++;
                                item = new ListViewItem(count + "", 0);
                                item.SubItems.Add(fileinfo.FullName);
                                pathList.Add(fileinfo.FullName);

                                StreamReader sr = new StreamReader(file);
                                string fileText = sr.ReadToEnd();
                                int fileLength = fileText.Length;
                                char ch;
                                int currentCount = 0;
                                for (int i = 0; i < fileLength; i++)
                                {
                                    ch = fileText[i];
                                    if ((int)ch == 10)
                                    {
                                        currentCount++;
                                    }
                                }
                                totalCount += currentCount + 1;
                                paraCount.Add(currentCount + 1);
                                item.SubItems.Add((currentCount + 1) + "");
                                lvList.Items.Add(item);
                                tbTotalCount.Text = totalCount + "";
                                System.Windows.Forms.Application.DoEvents();
                            }
                        }
                        catch (System.Exception ex)
                        {
                            Console.WriteLine(ex.ToString());
                        }
                    }
                }
                catch (System.Exception ex)
                {
                    {
                        Console.WriteLine(ex.Message);
                    }
                }
            }
        }

        public Boolean isCalculate(string extention)
        {
            int i = 0;
            if (myExtension.Count != 0)
            {
                int count = myExtension.Count;
                for (i = 0; i < count; i++)
                { 
                    if (extention == myExtension.ElementAt(i)) return true;
                    else continue;
                }
                return false;
            }
            else
            {
                int count = strExtension.Length;
                for (i = 0; i < count; i++)
                {
                    if (extention == strExtension[i]) return true;
                    else continue;
                }
            return false;
            }    
        }

        public Boolean isEliminate(string filename)
        {
            int i = 0;
            int count = extraExtension.Length;
            for (i = 0; i < count; i++)
            {
                if (filename.EndsWith(extraExtension[i]))
                    return false;
                else continue;
            }
            count = extraWord.Length;
            for (i = 0; i < count; i++)
            {
                if (filename.Contains(extraWord[i]))
                    return false;
                else continue;
            }
            return true;
        }

        public MainForm()
        {
            InitializeComponent();
            //calThread = new Thread(new ThreadStart(CalculateThread));
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            lvList.View = View.Details;
            lvList.Columns.Add("NO.");
            lvList.Columns.Add("                    path");
            lvList.Columns.Add(" count");
            lvList.Columns[0].Width = 40;
            lvList.Columns[1].Width = 519;
            lvList.Columns[2].Width = 60;
            lvList.FullRowSelect = true;
        }

        private void btnScan_Click(object sender, EventArgs e)
        {
            //实例化文件夹选择对话框
            FolderBrowserDialog fBD = new FolderBrowserDialog();
            if (fBD.ShowDialog() == DialogResult.Cancel)
            {
                //取消选择文件夹时重新聚焦路径选择按钮
                tbPath.Focus();
                return;
            }
            else
            {
                //选择好搜索路径时准备就绪并聚焦搜索按钮
                this.tbPath.Text = fBD.SelectedPath;
                strPath = this.tbPath.Text;
                btnCalculate.Focus();
            }      
        }

        private void tbPath_TextChanged(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(this.tbPath.Text))
                strPath = "";
            else
            {
                strPath = this.tbPath.Text;
            }
        }

        private void btnCalculate_Click(object sender, EventArgs e)
        {
            if (!Directory.Exists(strPath))
                strPath = System.Environment.CurrentDirectory;
            lvList.Items.Clear();
            totalCount=0;
            count = 0;
            thread=new Thread(new ThreadStart(CalculateThread));
            thread.IsBackground = true;
            thread.Start();
            //string strPath = System.Environment.CurrentDirectory;
            //isStartAnim = true;
            //calculate(strPath);
            //isStartAnim = false;
            //tbTotalCount.Text = totalCount + "";
        }

        private void CalculateThread()
        {
            Control.CheckForIllegalCrossThreadCalls = false;
            //string strPath = System.Environment.CurrentDirectory;
            btnCalculate.startAnim();
            contextMenuStrip2.Enabled = false;
            calculate(strPath);
            btnCalculate.stopAnim();
            contextMenuStrip2.Enabled = true;
            tbTotalCount.Text = totalCount + "";
        }

        private void btnClear_Click(object sender, EventArgs e)
        {
            lvList.Items.Clear();
            totalCount = 0;
            count = 0;
            tbTotalCount.Text = "";
            tbMyExtension.Text = ".";
            lbExtension.Items.Clear();
            myExtension.Clear();
        }

        private void tbMyExtension_TextChanged(object sender, EventArgs e)
        {
            String str = tbMyExtension.Text;
            if (str.StartsWith(".") && str.Length > 1)
            {
                btnAdd.Enabled = true;
            }
            else
            {
                btnAdd.Enabled = false;
            }
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            string str=tbMyExtension.Text;
            if (str == "" || str.IndexOf(".") !=0)
            {
                MessageBox.Show("无效的输入", "提示");
                return;
            }
            int extensionCount=myExtension.Count();
            for (int i = 0; i < extensionCount; i++)
            {
                if (str == myExtension.ElementAt(i))
                {

                    return;
                }
            }
            lbExtension.Items.Add(tbMyExtension.Text);
            myExtension.Add(tbMyExtension.Text);
            if (lbExtension.Items .Count== 1)
            {
                lbExtension.ContextMenuStrip=this.contextMenuStrip1;
            }
            tbMyExtension.Text = ".";
            tbMyExtension.SelectionStart = 1;
        }

        private void tbMyExtension_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyValue == 13)
            {
                String str = tbMyExtension.Text;
                if (str.StartsWith(".") && str.Length > 1)
                {
                    btnAdd_Click(sender, e);
                }
            }
        }

        private void 移除ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                if (lbExtension.Items.Count != 0)
                {
                    myExtension.RemoveAt(lbExtension.SelectedIndex);
                    lbExtension.Items.RemoveAt(lbExtension.SelectedIndex);
                }
                else
                {
                    lbExtension.ContextMenuStrip = null;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }

        private void cms_Opening(object sender, CancelEventArgs e)
        {
            String tempStr = lvList.Items[lvList.SelectedIndices[0]].SubItems[1].Text;
            cmsExtention.Text = "文件后缀:"+tempStr.Substring(tempStr.LastIndexOf(".")); ;
            Console.WriteLine(cmsExtention.Text);
        }

        private void openFile_Click(object sender, EventArgs e)
        {
            if (count > 0)
            {
                String path = lvList.Items[lvList.SelectedIndices[0]].SubItems[1].Text;
                Console.WriteLine(path);
                Process.Start("NOTEPAD.exe ", path);
            }
        }
        private void openPath_Click(object sender, EventArgs e)
        {
            if (count > 0)
            {
                String path = lvList.Items[lvList.SelectedIndices[0]].SubItems[1].Text;
                System.Diagnostics.ProcessStartInfo psi = new System.Diagnostics.ProcessStartInfo("Explorer.exe");
                psi.Arguments = " /select," + path;
                System.Diagnostics.Process.Start(psi);
            }
        }

        private void removeSingle_Click(object sender, EventArgs e)
        {
            if (count > 0)
            {
                ListView.SelectedIndexCollection collection=lvList.SelectedIndices;
                int selectCount = collection.Count;
                int[] selectIndexs = new int[selectCount];
                for (int i = selectCount - 1; i >= 0; i--)
                {
                    int selectIndex = selectIndexs[i]=collection[i];
                    totalCount -= Convert.ToInt32(lvList.Items[selectIndex].SubItems[2].Text);
                    tbTotalCount.Text = totalCount + "";
                    lvList.Items.RemoveAt(selectIndex);
                    paraCount.RemoveAt(selectIndex);
                }
                count -= selectCount;
                int length = lvList.Items.Count;
                int startIndex = selectIndexs[0];
                for (int j = startIndex; j < count; j++)
                {
                    lvList.Items[j].SubItems[0].Text = (j + 1) + "";
                }
            }
        }

        private void removeAll_Click(object sender, EventArgs e)
        {
            if (count > 0)
            {
                int selectIndex=lvList.SelectedIndices[0];
                String selectFileExtention = lvList.Items[selectIndex].SubItems[1].Text.Substring(lvList.Items[selectIndex].SubItems[1].Text.LastIndexOf("."));
                for (int i = 0; i < count;)
                {
                    if (lvList.Items[i].SubItems[1].Text.EndsWith(selectFileExtention))
                    {
                        totalCount -= Convert.ToInt16(lvList.Items[i].SubItems[2].Text);
                        lvList.Items.RemoveAt(i);
                        paraCount.RemoveAt(i);
                        count--;
                    }
                    else
                    {
                        i++;
                    }
                }
                for (int i = 0; i < count; i++)
                {
                    lvList.Items[i].SubItems[0].Text = (i + 1) + "";
                }
                tbTotalCount.Text = totalCount + "";
            }
        }

        private void MainForm_SizeChanged(object sender, EventArgs e)
        {
            if(lvList!=null&&lvList.Columns.Count>0)
                lvList.Columns[1].Width = this.Width - 156-121;
        }

        private void lvList_DoubleClick(object sender, EventArgs e)
        {
            String selectedStr = lvList.Items[lvList.SelectedIndices[0]].SubItems[1].Text;
            Console.WriteLine(selectedStr);
            Process.Start("NOTEPAD.exe ", selectedStr);
        }

        private void lvList_MouseHover(object sender, EventArgs e)
        {

        }

        private void lable3_Click(object sender, EventArgs e)
        {
            ExtensionManage ext = new ExtensionManage();
            List<String> extentionList = new List<string>();
            for (int i = 0; i < strExtension.Length; i++)
            {
                extentionList.Add(strExtension[i]);
            }
            ext.ExtentionList = extentionList;
            //ext.Show();
        }

        private void MainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            Application.Exit();
            if (thread != null)
            {
                if (thread.IsAlive)
                {
                    thread.Interrupt();
                }
            }
        }
    }
}
