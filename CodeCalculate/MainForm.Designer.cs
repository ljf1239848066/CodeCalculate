namespace CodeCalculate
{
    partial class MainForm
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.btnClear = new System.Windows.Forms.Button();
            this.tbTotalCount = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.lable3 = new System.Windows.Forms.Label();
            this.tbMyExtension = new System.Windows.Forms.TextBox();
            this.btnAdd = new System.Windows.Forms.Button();
            this.lbExtension = new System.Windows.Forms.ListBox();
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.移除ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.contextMenuStrip2 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.openFile = new System.Windows.Forms.ToolStripMenuItem();
            this.openPath = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.removeSingle = new System.Windows.Forms.ToolStripMenuItem();
            this.removeAll = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator3 = new System.Windows.Forms.ToolStripSeparator();
            this.cmsExtention = new System.Windows.Forms.ToolStripMenuItem();
            this.tbPath = new System.Windows.Forms.TextBox();
            this.btnScan = new System.Windows.Forms.Button();
            this.folderBrowserDialog1 = new System.Windows.Forms.FolderBrowserDialog();
            this.btnCalculate = new CodeCalculate.LoadButton();
            this.lvList = new CodeCalculate.CustomListView();
            this.contextMenuStrip1.SuspendLayout();
            this.contextMenuStrip2.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnClear
            // 
            this.btnClear.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnClear.Location = new System.Drawing.Point(664, 437);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(83, 33);
            this.btnClear.TabIndex = 9;
            this.btnClear.Text = "清除";
            this.btnClear.UseVisualStyleBackColor = true;
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // tbTotalCount
            // 
            this.tbTotalCount.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.tbTotalCount.Enabled = false;
            this.tbTotalCount.Location = new System.Drawing.Point(664, 123);
            this.tbTotalCount.Name = "tbTotalCount";
            this.tbTotalCount.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.tbTotalCount.Size = new System.Drawing.Size(81, 21);
            this.tbTotalCount.TabIndex = 13;
            // 
            // label2
            // 
            this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(673, 105);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(65, 12);
            this.label2.TabIndex = 14;
            this.label2.Text = "代码总行数";
            // 
            // lable3
            // 
            this.lable3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.lable3.AutoSize = true;
            this.lable3.Location = new System.Drawing.Point(662, 153);
            this.lable3.Name = "lable3";
            this.lable3.Size = new System.Drawing.Size(89, 12);
            this.lable3.TabIndex = 16;
            this.lable3.Text = "自定义文件格式";
            this.lable3.Click += new System.EventHandler(this.lable3_Click);
            // 
            // tbMyExtension
            // 
            this.tbMyExtension.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.tbMyExtension.Font = new System.Drawing.Font("宋体", 10F);
            this.tbMyExtension.Location = new System.Drawing.Point(664, 175);
            this.tbMyExtension.MaxLength = 8;
            this.tbMyExtension.Name = "tbMyExtension";
            this.tbMyExtension.Size = new System.Drawing.Size(43, 23);
            this.tbMyExtension.TabIndex = 17;
            this.tbMyExtension.TextChanged += new System.EventHandler(this.tbMyExtension_TextChanged);
            this.tbMyExtension.KeyDown += new System.Windows.Forms.KeyEventHandler(this.tbMyExtension_KeyDown);
            // 
            // btnAdd
            // 
            this.btnAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnAdd.Enabled = false;
            this.btnAdd.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.btnAdd.Font = new System.Drawing.Font("宋体", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnAdd.Location = new System.Drawing.Point(707, 175);
            this.btnAdd.Margin = new System.Windows.Forms.Padding(0);
            this.btnAdd.Name = "btnAdd";
            this.btnAdd.Size = new System.Drawing.Size(38, 23);
            this.btnAdd.TabIndex = 18;
            this.btnAdd.Text = "       ";
            this.btnAdd.UseVisualStyleBackColor = true;
            this.btnAdd.Click += new System.EventHandler(this.btnAdd_Click);
            // 
            // lbExtension
            // 
            this.lbExtension.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.lbExtension.FormattingEnabled = true;
            this.lbExtension.ItemHeight = 12;
            this.lbExtension.Location = new System.Drawing.Point(664, 205);
            this.lbExtension.Name = "lbExtension";
            this.lbExtension.Size = new System.Drawing.Size(81, 220);
            this.lbExtension.TabIndex = 19;
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.移除ToolStripMenuItem});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(164, 26);
            // 
            // 移除ToolStripMenuItem
            // 
            this.移除ToolStripMenuItem.Name = "移除ToolStripMenuItem";
            this.移除ToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.D)));
            this.移除ToolStripMenuItem.Size = new System.Drawing.Size(163, 22);
            this.移除ToolStripMenuItem.Text = "移除(&D)";
            this.移除ToolStripMenuItem.Click += new System.EventHandler(this.移除ToolStripMenuItem_Click);
            // 
            // contextMenuStrip2
            // 
            this.contextMenuStrip2.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.openFile,
            this.openPath,
            this.toolStripSeparator1,
            this.removeSingle,
            this.removeAll,
            this.toolStripSeparator3,
            this.cmsExtention});
            this.contextMenuStrip2.Name = "contextMenuStrip2";
            this.contextMenuStrip2.Size = new System.Drawing.Size(161, 126);
            this.contextMenuStrip2.Opening += new System.ComponentModel.CancelEventHandler(this.cms_Opening);
            // 
            // openFile
            // 
            this.openFile.Name = "openFile";
            this.openFile.Size = new System.Drawing.Size(160, 22);
            this.openFile.Text = "打开文件";
            this.openFile.Click += new System.EventHandler(this.openFile_Click);
            // 
            // openPath
            // 
            this.openPath.Name = "openPath";
            this.openPath.Size = new System.Drawing.Size(160, 22);
            this.openPath.Text = "打开文件目录";
            this.openPath.Click += new System.EventHandler(this.openPath_Click);
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(157, 6);
            // 
            // removeSingle
            // 
            this.removeSingle.Name = "removeSingle";
            this.removeSingle.Size = new System.Drawing.Size(160, 22);
            this.removeSingle.Text = "排除选中文件";
            this.removeSingle.Click += new System.EventHandler(this.removeSingle_Click);
            // 
            // removeAll
            // 
            this.removeAll.Name = "removeAll";
            this.removeAll.Size = new System.Drawing.Size(160, 22);
            this.removeAll.Text = "排除同类型文件";
            this.removeAll.Click += new System.EventHandler(this.removeAll_Click);
            // 
            // toolStripSeparator3
            // 
            this.toolStripSeparator3.Name = "toolStripSeparator3";
            this.toolStripSeparator3.Size = new System.Drawing.Size(157, 6);
            // 
            // cmsExtention
            // 
            this.cmsExtention.Enabled = false;
            this.cmsExtention.Name = "cmsExtention";
            this.cmsExtention.Size = new System.Drawing.Size(160, 22);
            this.cmsExtention.Text = "Extention";
            // 
            // tbPath
            // 
            this.tbPath.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.tbPath.Location = new System.Drawing.Point(5, 7);
            this.tbPath.Name = "tbPath";
            this.tbPath.Size = new System.Drawing.Size(602, 21);
            this.tbPath.TabIndex = 20;
            this.tbPath.TextChanged += new System.EventHandler(this.tbPath_TextChanged);
            // 
            // btnScan
            // 
            this.btnScan.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnScan.Location = new System.Drawing.Point(610, 7);
            this.btnScan.Name = "btnScan";
            this.btnScan.Size = new System.Drawing.Size(37, 23);
            this.btnScan.TabIndex = 21;
            this.btnScan.Text = "浏览";
            this.btnScan.UseVisualStyleBackColor = true;
            this.btnScan.Click += new System.EventHandler(this.btnScan_Click);
            // 
            // folderBrowserDialog1
            // 
            this.folderBrowserDialog1.RootFolder = System.Environment.SpecialFolder.History;
            // 
            // btnCalculate
            // 
            this.btnCalculate.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnCalculate.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.btnCalculate.IsStartAnim = false;
            this.btnCalculate.Location = new System.Drawing.Point(664, 7);
            this.btnCalculate.Name = "btnCalculate";
            this.btnCalculate.Size = new System.Drawing.Size(80, 80);
            this.btnCalculate.TabIndex = 8;
            this.btnCalculate.Text = "统计";
            this.btnCalculate.UseVisualStyleBackColor = true;
            this.btnCalculate.Click += new System.EventHandler(this.btnCalculate_Click);
            // 
            // lvList
            // 
            this.lvList.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                        | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.lvList.ContextMenuStrip = this.contextMenuStrip2;
            this.lvList.Font = new System.Drawing.Font("宋体", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lvList.Location = new System.Drawing.Point(5, 34);
            this.lvList.Name = "lvList";
            this.lvList.Size = new System.Drawing.Size(642, 436);
            this.lvList.TabIndex = 15;
            this.lvList.UseCompatibleStateImageBehavior = false;
            this.lvList.DoubleClick += new System.EventHandler(this.lvList_DoubleClick);
            this.lvList.MouseHover += new System.EventHandler(this.lvList_MouseHover);
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(756, 475);
            this.Controls.Add(this.btnScan);
            this.Controls.Add(this.tbPath);
            this.Controls.Add(this.btnCalculate);
            this.Controls.Add(this.lbExtension);
            this.Controls.Add(this.btnAdd);
            this.Controls.Add(this.tbMyExtension);
            this.Controls.Add(this.lable3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.tbTotalCount);
            this.Controls.Add(this.btnClear);
            this.Controls.Add(this.lvList);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MinimumSize = new System.Drawing.Size(772, 513);
            this.Name = "MainForm";
            this.Text = "项目代码统计";
            this.Load += new System.EventHandler(this.MainForm_Load);
            this.SizeChanged += new System.EventHandler(this.MainForm_SizeChanged);
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.MainForm_FormClosing);
            this.contextMenuStrip1.ResumeLayout(false);
            this.contextMenuStrip2.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnClear;
        private System.Windows.Forms.TextBox tbTotalCount;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label lable3;
        private System.Windows.Forms.TextBox tbMyExtension;
        private System.Windows.Forms.Button btnAdd;
        private System.Windows.Forms.ListBox lbExtension;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.ToolStripMenuItem 移除ToolStripMenuItem;
        private CustomListView lvList;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip2;
        private System.Windows.Forms.ToolStripMenuItem removeSingle;
        private System.Windows.Forms.ToolStripMenuItem removeAll;
        private System.Windows.Forms.ToolStripMenuItem openPath;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripMenuItem openFile;
        private System.Windows.Forms.ToolStripMenuItem cmsExtention;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator3;
        private System.Windows.Forms.TextBox tbPath;
        private System.Windows.Forms.Button btnScan;
        private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog1;
        private LoadButton btnCalculate;
    }
}

