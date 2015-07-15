using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace CodeCalculate
{
    public partial class ExtensionManage : Form
    {
        private List<String> extentionList;
        private List<List<String>> extensions;
        private List<TextBox> tbExtentionList;
        private bool addOrEdit=false;
        private int editIndex;

        public List<String> ExtentionList
        {
            get { return extentionList; }
            set { extentionList = value; }
        }

        public ExtensionManage()
        {
            InitializeComponent();
        }

        private void ExtensionManage_Load(object sender, EventArgs e)
        {
            paList.View = View.Details;
            paList.Columns.Add("NO.");
            paList.Columns.Add("     name");
            paList.Columns[0].Width = 30;
            paList.Columns[1].Width = 115;
            paList.FullRowSelect = true;
            ListViewItem item = new ListViewItem("1", 0);
            item.SubItems.Add("默认统计类型");
            paList.Items.Add(item);

            extensions = new List<List<String>>();
            List<String> tempList = new List<string>(ExtentionList);
            extensions.Add(tempList);

            initSubAssemble();
        }

        private void paList_ItemSelectionChanged(object sender, ListViewItemSelectionChangedEventArgs e)
        {
            try
            {
                editIndex = paList.SelectedIndices[0];
                List<String> tempList=extensions[editIndex];
                extentionList.Clear();
                for (int i = 0; i < tempList.Count; i++)
                {
                    extentionList.Add(tempList[i]);
                }
                //extentionList = extensions[editIndex];
                addOrEdit = false;
                initSubAssemble();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            addItems();
        }

        private void btnRemove_Click(object sender, EventArgs e)
        {

        }

        private void btnAddFinished_Click(object sender, EventArgs e)
        {
            if (addOrEdit)
            {
                List<String> tempList = new List<string>();
                int count=extentionList.Count;
                for (int i = 0; i < count; i++)
                {
                    tempList.Add(extentionList[i]);
                }
                extensions.Add(tempList);
                count = paList.Items.Count;
                ListViewItem item = new ListViewItem("" + (count + 1), 0);
                item.SubItems.Add("文件类型集" + count);
                paList.Items.Add(item);

                addItems();
            }
            else
            {
                //extensions.ElementAt
            }
        }

        private void btnAddCancel_Click(object sender, EventArgs e)
        {
            ExtentionList.Clear();
            tbExtentionList.Clear();
            subList.Controls.Clear();
        }

        private void btnSave_Click(object sender, EventArgs e)
        {

        }

        private void btnImport_Click(object sender, EventArgs e)
        {

        }

        private void btnEnsure_Click(object sender, EventArgs e)
        {

        }

        private void btnCancel_Click(object sender, EventArgs e)
        {

        }

        public void initSubAssemble()
        {
            int count = ExtentionList.Count;
            int width = subList.Width - 5;
            subList.Controls.Clear();
            if (count > 10)
            {
                if (count >= 15)
                    width = subList.Width - 22;
                tbExtentionList = new List<TextBox>();
                System.Drawing.Point point;
                TextBox tbExtention;
                for (int i = 0; i <= count; i++)
                {
                    point = new Point(1, i * 20);
                    tbExtention = new TextBox();
                    tbExtention.Width = width;
                    tbExtention.Height = 20;
                    if (i == count)
                    {
                        tbExtention.Text = "双击添加新项";
                    }
                    else
                    {
                        tbExtention.Text = ExtentionList[i];
                    }
                    tbExtention.Location = point;
                    subList.Controls.Add(tbExtention);
                    tbExtention.Tag = i;
                    tbExtention.ReadOnly = true;
                    tbExtention.DoubleClick += new System.EventHandler(this.tbEdit_DoubleClick);
                    tbExtention.MouseLeave += new System.EventHandler(this.tbEdit_MoueseLeave);
                    tbExtentionList.Add(tbExtention);
                }
            }
        }

        private void tbEdit_DoubleClick(object sender, EventArgs e)
        {
            TextBox tbEdit = (TextBox)sender;
            tbEdit.ReadOnly = false;
            lblTip.Visible = false;
            if (tbEdit == tbExtentionList[ExtentionList.Count] || (addOrEdit && ExtentionList.Count == 1))
            {
                tbEdit.Text = ".";
                tbEdit.SelectionStart = 1;
            }
        }
        private void tbEdit_MoueseLeave(object sender, EventArgs e)
        {
            TextBox tbEdit = (TextBox)sender;
            tbEdit.ReadOnly = true;
            int count = ExtentionList.Count;
            if (tbEdit == tbExtentionList[count])
            {
                String tbEditText = tbEdit.Text;
                if (tbEditText.StartsWith(".") && tbEditText.Length > 1)
                {
                    if (ExtentionList.IndexOf(tbEditText) >= 0)
                    {
                        lblTip.Visible = true;
                    }
                    else
                    {
                        ExtentionList.Add(tbEditText);
                        Point point = tbExtentionList.ElementAt(count).Location;
                        point.Y += 20;
                        TextBox tbExtention = new TextBox();
                        int width = subList.Width - 5;
                        if (count >= 15)
                            width = subList.Width - 22;
                        tbExtention.Width = width;
                        tbExtention.Height = 20;
                        tbExtention.Text = "双击添加新项";
                        tbExtention.Location = point;
                        subList.Controls.Add(tbExtention);
                        tbExtention.Tag = count + 1;
                        tbExtention.ReadOnly = true;
                        tbExtention.DoubleClick += new System.EventHandler(this.tbEdit_DoubleClick);
                        tbExtention.MouseLeave += new System.EventHandler(this.tbEdit_MoueseLeave);
                        tbExtentionList.Add(tbExtention);
                    }
                }
                else
                {
                    tbEdit.Text = "双击添加新项";
                }
                
            }
        }
        private void addItems()
        {
            extentionList = new List<string>();
            tbExtentionList.Clear();
            subList.Controls.Clear();

            Point point = new Point(1, 0);
            TextBox tbExtention = new TextBox();
            tbExtention.Width = subList.Width - 5;
            tbExtention.Height = 20;
            tbExtention.Text = "双击添加新项";
            tbExtention.Location = point;
            subList.Controls.Add(tbExtention);
            tbExtention.Tag = 1;
            tbExtention.ReadOnly = true;
            tbExtention.DoubleClick += new System.EventHandler(this.tbEdit_DoubleClick);
            tbExtention.MouseLeave += new System.EventHandler(this.tbEdit_MoueseLeave);
            tbExtentionList.Add(tbExtention);
            addOrEdit = true;
        }
    }
}
