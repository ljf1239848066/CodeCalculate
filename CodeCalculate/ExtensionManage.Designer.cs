namespace CodeCalculate
{
    partial class ExtensionManage
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ExtensionManage));
            this.label1 = new System.Windows.Forms.Label();
            this.btnAdd = new System.Windows.Forms.Button();
            this.btnRemove = new System.Windows.Forms.Button();
            this.btnAddFinished = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.btnImport = new System.Windows.Forms.Button();
            this.btnSave = new System.Windows.Forms.Button();
            this.subList = new System.Windows.Forms.Panel();
            this.btnEnsure = new System.Windows.Forms.Button();
            this.btnCancel = new System.Windows.Forms.Button();
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.lblTip = new System.Windows.Forms.Label();
            this.btnAddCancel = new System.Windows.Forms.Button();
            this.paList = new CodeCalculate.CustomListView();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(27, 24);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(60, 15);
            this.label1.TabIndex = 25;
            this.label1.Text = "选择项:";
            // 
            // btnAdd
            // 
            this.btnAdd.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnAdd.Location = new System.Drawing.Point(25, 464);
            this.btnAdd.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btnAdd.Name = "btnAdd";
            this.btnAdd.Size = new System.Drawing.Size(93, 38);
            this.btnAdd.TabIndex = 26;
            this.btnAdd.Text = "添加";
            this.btnAdd.UseVisualStyleBackColor = true;
            this.btnAdd.Click += new System.EventHandler(this.btnAdd_Click);
            // 
            // btnRemove
            // 
            this.btnRemove.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnRemove.Location = new System.Drawing.Point(131, 462);
            this.btnRemove.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btnRemove.Name = "btnRemove";
            this.btnRemove.Size = new System.Drawing.Size(93, 38);
            this.btnRemove.TabIndex = 27;
            this.btnRemove.Text = "移除";
            this.btnRemove.UseVisualStyleBackColor = true;
            this.btnRemove.Click += new System.EventHandler(this.btnRemove_Click);
            // 
            // btnAddFinished
            // 
            this.btnAddFinished.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnAddFinished.Location = new System.Drawing.Point(276, 462);
            this.btnAddFinished.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btnAddFinished.Name = "btnAddFinished";
            this.btnAddFinished.Size = new System.Drawing.Size(93, 38);
            this.btnAddFinished.TabIndex = 30;
            this.btnAddFinished.Text = "完成";
            this.btnAddFinished.UseVisualStyleBackColor = true;
            this.btnAddFinished.Click += new System.EventHandler(this.btnAddFinished_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(277, 24);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(75, 15);
            this.label2.TabIndex = 29;
            this.label2.Text = "编辑子项:";
            // 
            // btnImport
            // 
            this.btnImport.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnImport.Location = new System.Drawing.Point(544, 198);
            this.btnImport.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btnImport.Name = "btnImport";
            this.btnImport.Size = new System.Drawing.Size(95, 38);
            this.btnImport.TabIndex = 33;
            this.btnImport.Text = "导入";
            this.btnImport.UseVisualStyleBackColor = true;
            this.btnImport.Click += new System.EventHandler(this.btnImport_Click);
            // 
            // btnSave
            // 
            this.btnSave.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnSave.Location = new System.Drawing.Point(544, 136);
            this.btnSave.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btnSave.Name = "btnSave";
            this.btnSave.Size = new System.Drawing.Size(95, 38);
            this.btnSave.TabIndex = 32;
            this.btnSave.Text = "保存";
            this.btnSave.UseVisualStyleBackColor = true;
            this.btnSave.Click += new System.EventHandler(this.btnSave_Click);
            // 
            // subList
            // 
            this.subList.AutoScroll = true;
            this.subList.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.subList.Location = new System.Drawing.Point(276, 46);
            this.subList.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.subList.Name = "subList";
            this.subList.Size = new System.Drawing.Size(199, 380);
            this.subList.TabIndex = 34;
            // 
            // btnEnsure
            // 
            this.btnEnsure.Font = new System.Drawing.Font("华文新魏", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnEnsure.Location = new System.Drawing.Point(544, 300);
            this.btnEnsure.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btnEnsure.Name = "btnEnsure";
            this.btnEnsure.Size = new System.Drawing.Size(93, 62);
            this.btnEnsure.TabIndex = 35;
            this.btnEnsure.Text = "确认";
            this.btnEnsure.UseVisualStyleBackColor = true;
            this.btnEnsure.Click += new System.EventHandler(this.btnEnsure_Click);
            // 
            // btnCancel
            // 
            this.btnCancel.Font = new System.Drawing.Font("华文新魏", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnCancel.Location = new System.Drawing.Point(544, 368);
            this.btnCancel.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(93, 62);
            this.btnCancel.TabIndex = 36;
            this.btnCancel.Text = "取消";
            this.btnCancel.UseVisualStyleBackColor = true;
            this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.FileName = "openFileDialog1";
            // 
            // lblTip
            // 
            this.lblTip.ForeColor = System.Drawing.Color.Red;
            this.lblTip.Location = new System.Drawing.Point(276, 431);
            this.lblTip.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lblTip.Name = "lblTip";
            this.lblTip.Size = new System.Drawing.Size(199, 29);
            this.lblTip.TabIndex = 37;
            this.lblTip.Text = "* 已有重复内容";
            this.lblTip.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            this.lblTip.Visible = false;
            // 
            // btnAddCancel
            // 
            this.btnAddCancel.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnAddCancel.Location = new System.Drawing.Point(383, 462);
            this.btnAddCancel.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btnAddCancel.Name = "btnAddCancel";
            this.btnAddCancel.Size = new System.Drawing.Size(93, 38);
            this.btnAddCancel.TabIndex = 38;
            this.btnAddCancel.Text = "取消";
            this.btnAddCancel.UseVisualStyleBackColor = true;
            this.btnAddCancel.Click += new System.EventHandler(this.btnAddCancel_Click);
            // 
            // paList
            // 
            this.paList.BackColor = System.Drawing.SystemColors.Control;
            this.paList.Location = new System.Drawing.Point(25, 46);
            this.paList.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.paList.MultiSelect = false;
            this.paList.Name = "paList";
            this.paList.Size = new System.Drawing.Size(199, 380);
            this.paList.TabIndex = 24;
            this.paList.UseCompatibleStateImageBehavior = false;
            this.paList.ItemSelectionChanged += new System.Windows.Forms.ListViewItemSelectionChangedEventHandler(this.paList_ItemSelectionChanged);
            // 
            // ExtensionManage
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(689, 540);
            this.ControlBox = false;
            this.Controls.Add(this.btnAddCancel);
            this.Controls.Add(this.lblTip);
            this.Controls.Add(this.btnCancel);
            this.Controls.Add(this.btnEnsure);
            this.Controls.Add(this.subList);
            this.Controls.Add(this.btnImport);
            this.Controls.Add(this.btnSave);
            this.Controls.Add(this.btnAddFinished);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.btnRemove);
            this.Controls.Add(this.btnAdd);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.paList);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Name = "ExtensionManage";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "文件类型";
            this.Load += new System.EventHandler(this.ExtensionManage_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private CustomListView paList;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button btnAdd;
        private System.Windows.Forms.Button btnRemove;
        private System.Windows.Forms.Button btnAddFinished;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button btnImport;
        private System.Windows.Forms.Button btnSave;
        private System.Windows.Forms.Panel subList;
        private System.Windows.Forms.Button btnEnsure;
        private System.Windows.Forms.Button btnCancel;
        private System.Windows.Forms.OpenFileDialog openFileDialog1;
        private System.Windows.Forms.Label lblTip;
        private System.Windows.Forms.Button btnAddCancel;

    }
}