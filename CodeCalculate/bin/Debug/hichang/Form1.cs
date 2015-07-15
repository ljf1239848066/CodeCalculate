using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace BaofengUpdate
{
    public partial class Form1 : Form
    {
        private int count = 0;
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("升毛线级呀！烦不烦！", "暴风提示!") == DialogResult.OK) this.Close();
        }

        private void Form1_Load(object sender, EventArgs e)
        {   
            try
            {
                StreamReader sr = new StreamReader("countupdate.dat");
                count = Convert.ToInt16(sr.ReadToEnd());
                sr.Close();
            }
            catch
            {
            }
            count++;
            this.label1.Text = "你妹的，这是你第" + count.ToString() + "升级！";
            FileStream fs = new FileStream("countupdate.dat", FileMode.OpenOrCreate, FileAccess.Write);
            StreamWriter sw = new StreamWriter(fs);
            sw.Write(count.ToString(), true);
            sw.Close();

        }
    }
}
